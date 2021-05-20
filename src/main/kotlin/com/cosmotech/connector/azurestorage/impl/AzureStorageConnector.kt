// copyright (c) cosmo tech corporation.
// licensed under the mit license.

package com.cosmotech.connector.azurestorage.impl

import com.cosmotech.connector.commons.Connector
import com.cosmotech.connector.commons.pojo.CsvData
import com.cosmotech.connector.azurestorage.utils.AzureStorageConnectorUtil
import org.apache.logging.log4j.LogManager
import java.io.File
import com.azure.storage.blob.BlobContainerClient
import com.azure.storage.blob.BlobContainerClientBuilder
import com.azure.storage.blob.models.ListBlobsOptions
import com.azure.storage.blob.models.BlobItem
import com.azure.core.http.rest.PagedIterable
import java.time.Duration


/**
 * Connector for Azure Storage
 */
class AzureStorageConnector : Connector<BlobContainerClient, PagedIterable<BlobItem>, Unit> {
    var containerName: String? = null
    var prefix: String? = null

    companion object {
        val LOGGER = LogManager.getLogger(AzureStorageConnector::class.java.name)
    }

    override fun createClient(): BlobContainerClient {
        LOGGER.info("Create Azure Storage Container Client")
        val azurePath = AzureStorageConnectorUtil.getAzureStorageContainerBlobPrefix()
        val splitPath = azurePath.split('/', limit = 2)
        this.containerName = splitPath[0]
        LOGGER.debug("Container name: ${this.containerName}")
        if (1 < splitPath.size) {
          this.prefix = splitPath[1]
          LOGGER.debug("Prefix filter set: ${this.prefix}")
        }
        return BlobContainerClientBuilder()
            .connectionString(AzureStorageConnectorUtil.getAzureStorageConnectionString())
            .containerName(this.containerName)
            .buildClient()
    }

    override fun prepare(client: BlobContainerClient): PagedIterable<BlobItem> {
        LOGGER.info("Start preparing Azure Storage data")
        val options = ListBlobsOptions()
        if (this.prefix != null) options.setPrefix(prefix)
        return client.listBlobs(options, null)
    }

    override fun process() {
        LOGGER.info("Azure Storage Connector start")
        val client = this.createClient()
        val preparedData = this.prepare(client)
        preparedData.forEach {
          val blobName = it.getName()
          var localRelativeFile = blobName.removePrefix(this.prefix ?: "")
          if (localRelativeFile.startsWith("/")) {
            localRelativeFile = localRelativeFile.drop(1)
          }
          LOGGER.debug("Local relative file: ${localRelativeFile}")

          var localAbsolutePath = AzureStorageConnectorUtil.getExportCsvFilesPath()
          if (!localAbsolutePath.endsWith("/") ) {
            localAbsolutePath = localAbsolutePath.plus("/")
          }
          localAbsolutePath = localAbsolutePath + localRelativeFile
          LOGGER.debug("Local absolute path: ${localAbsolutePath}")

          val localAbsoluteFile = File(localAbsolutePath)
          val localAbsoluteDir = File(localAbsoluteFile.getParent())
          localAbsoluteDir.mkdirs()

          LOGGER.info("Downloading blob ${blobName}")
          val blobClient = client.getBlobClient(blobName)
          blobClient.downloadToFile(localAbsolutePath, true)
        }

        LOGGER.info("Azure Storage Connector finished")
    }
}
