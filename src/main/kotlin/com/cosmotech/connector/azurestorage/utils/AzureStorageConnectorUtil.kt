// copyright (c) cosmo tech corporation.
// licensed under the mit license.

package com.cosmotech.connector.azurestorage.utils

import com.cosmotech.connector.azurestorage.constants.*
import org.eclipse.microprofile.config.Config
import org.eclipse.microprofile.config.ConfigProvider

/**
 * Utility class to handle Azure Digital Twins objects
 */
class AzureStorageConnectorUtil {

    companion object Builder {

        private val configuration: Config = ConfigProvider.getConfig()

        /** Get the ADT instance URL*/
        @JvmStatic
        fun getAzureStorageConnectionString(): String {
            return configuration.getValue(AZURE_STORAGE_CONNECTION_STRING,String::class.java)
        }

        /** Get the ADT instance URL*/
        @JvmStatic
        fun getAzureStorageContainerBlobPrefix(): String {
            return configuration.getValue(AZURE_STORAGE_CONTAINER_BLOB_PREFIX,String::class.java)
        }

        /** Get the ADT instance URL*/
        @JvmStatic
        fun getExportCsvFilesPath(): String {
            return configuration.getValue(CSM_FETCH_ABSOLUTE_PATH,String::class.java)
        }
    }
}
