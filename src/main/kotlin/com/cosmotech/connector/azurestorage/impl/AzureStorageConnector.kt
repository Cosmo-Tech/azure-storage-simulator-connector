// copyright (c) cosmo tech corporation.
// licensed under the mit license.

package com.cosmotech.connector.azurestorage.impl

import com.cosmotech.connector.commons.Connector
import com.cosmotech.connector.commons.pojo.CsvData
import com.cosmotech.connector.azurestorage.utils.AzureStorageConnectorUtil
import org.apache.logging.log4j.LogManager
import java.io.File
import com.azure.storage.blob.BlobServiceClient
import com.azure.storage.blob.BlobServiceClientBuilder
import com.azure.storage.blob.models.BlobItem


/**
 * Connector for Azure Storage
 */
class AzureStorageConnector : Connector<BlobServiceClient, List<BlobItem>, Unit> {

    companion object {
        val LOGGER = LogManager.getLogger(AzureStorageConnector::class.java.name)
    }

    override fun createClient(): BlobServiceClient {
        LOGGER.info("Create Azure Client")
        return BlobServiceClientBuilder()
            .connectionString(AzureStorageConnectorUtil.getAzureStorageConnectionString())
            .buildClient()
    }

    override fun prepare(client: BlobServiceClient): List<BlobItem> {
        LOGGER.info("Start preparing Azure Storage data")
        return listOf()
    }

    override fun process() {
        val client = this.createClient()
        val preparedData = this.prepare(client)
    }
}
