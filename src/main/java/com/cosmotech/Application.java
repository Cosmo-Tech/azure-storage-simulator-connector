// copyright (c) cosmo tech corporation.
// licensed under the mit license.

package com.cosmotech;

import com.cosmotech.connector.azurestorage.impl.AzureStorageConnector;

public class Application {

  public static void main( String[] args ){

    final AzureStorageConnector azureStorageConnector = new AzureStorageConnector();
    azureStorageConnector.process();

  }
}
