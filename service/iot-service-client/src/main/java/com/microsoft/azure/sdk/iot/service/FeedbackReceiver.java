/*
 * Copyright (c) Microsoft. All rights reserved.
 * Licensed under the MIT license. See LICENSE file in the project root for full license information.
 */

package com.microsoft.azure.sdk.iot.service;

import com.microsoft.azure.sdk.iot.service.transport.amqps.AmqpReceive;
import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * FeedbackReceiver is a specialized receiver whose ReceiveAsync
 * method returns a FeedbackBatch instead of a Message.
 */
@Slf4j
public class FeedbackReceiver extends Receiver
{
    private final long DEFAULT_TIMEOUT_MS = 60000;
    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    private String deviceId;
    private AmqpReceive amqpReceive;

    /**
     * Constructor to verify initialization parameters
     * Create instance of AmqpReceive
     * @deprecated As of release 1.1.15, replaced by {@link #FeedbackReceiver(String hostName, String userName, String sasToken, IotHubServiceClientProtocol iotHubServiceClientProtocol)}
     * @param hostName The iot hub host name
     * @param userName The iot hub user name
     * @param sasToken The iot hub SAS token for the given device
     * @param iotHubServiceClientProtocol The iot hub protocol name
     * @param deviceId The device id
     */
    public @Deprecated FeedbackReceiver(String hostName, String userName, String sasToken, IotHubServiceClientProtocol iotHubServiceClientProtocol, String deviceId)
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_001: [The constructor shall throw IllegalArgumentException if any the input string is null or empty]
        if (Tools.isNullOrEmpty(hostName))
        {
            throw new IllegalArgumentException("hostName cannot be null or empty");
        }
        if (Tools.isNullOrEmpty(userName))
        {
            throw new IllegalArgumentException("userName cannot be null or empty");
        }
        if (Tools.isNullOrEmpty(sasToken))
        {
            throw new IllegalArgumentException("sasToken cannot be null or empty");
        }
        if (Tools.isNullOrEmpty(deviceId))
        {
            throw new IllegalArgumentException("deviceId cannot be null or empty");
        }
        
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_002: [The constructor shall store deviceId]
        this.deviceId = deviceId;
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_003: [The constructor shall create a new instance of AmqpReceive object]
        this.amqpReceive = new AmqpReceive(hostName, userName, sasToken, iotHubServiceClientProtocol);
    }

    /**
     * Constructor to verify initialization parameters
     * Create instance of AmqpReceive
     *
     * @param hostName The iot hub host name
     * @param userName The iot hub user name
     * @param sasToken The iot hub SAS token for the given device
     * @param iotHubServiceClientProtocol protocol to be used
     *
     */
    public FeedbackReceiver(String hostName, String userName, String sasToken, IotHubServiceClientProtocol iotHubServiceClientProtocol)
    {
        this(hostName, userName, sasToken, iotHubServiceClientProtocol, (ProxyOptions) null);
    }

    /**
     * Constructor to verify initialization parameters
     * Create instance of AmqpReceive
     *
     * @param hostName The iot hub host name
     * @param userName The iot hub user name
     * @param sasToken The iot hub SAS token for the given device
     * @param iotHubServiceClientProtocol protocol to be used
     * @param proxyOptions the proxy options to tunnel through, if a proxy should be used.
     */
    public FeedbackReceiver(String hostName, String userName, String sasToken, IotHubServiceClientProtocol iotHubServiceClientProtocol, ProxyOptions proxyOptions)
    {
        this(hostName, userName, sasToken, iotHubServiceClientProtocol, proxyOptions, null);
    }

    /**
     * Constructor to verify initialization parameters
     * Create instance of AmqpReceive
     *
     * @param hostName The iot hub host name
     * @param userName The iot hub user name
     * @param sasToken The iot hub SAS token for the given device
     * @param iotHubServiceClientProtocol protocol to be used
     * @param proxyOptions the proxy options to tunnel through, if a proxy should be used.
     * @param sslContext the SSL context to use during the TLS handshake when opening the connection. If null, a default
     *                   SSL context will be generated. This default SSLContext trusts the IoT Hub public certificates.
     */
    public FeedbackReceiver(String hostName, String userName, String sasToken, IotHubServiceClientProtocol iotHubServiceClientProtocol, ProxyOptions proxyOptions, SSLContext sslContext)
    {
        if (Tools.isNullOrEmpty(hostName))
        {
            throw new IllegalArgumentException("hostName cannot be null or empty");
        }
        if (Tools.isNullOrEmpty(userName))
        {
            throw new IllegalArgumentException("userName cannot be null or empty");
        }
        if (Tools.isNullOrEmpty(sasToken))
        {
            throw new IllegalArgumentException("sasToken cannot be null or empty");
        }

        if (iotHubServiceClientProtocol  == null)
        {
            throw new IllegalArgumentException("iotHubServiceClientProtocol cannot be null");
        }

        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_003: [The constructor shall create a new instance of AmqpReceive object]
        this.amqpReceive = new AmqpReceive(hostName, userName, sasToken, iotHubServiceClientProtocol, proxyOptions, sslContext);
    }
        
    /**
     * Open AmqpReceive object
     *
     * @throws IOException This exception is thrown if the input AmqpReceive object is null
     */
    public void open() throws IOException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_004: [The function shall throw IOException if the member AMQPReceive object has not been initialized]
        if (this.amqpReceive == null)
        {
            throw new IOException("AMQP receiver is not initialized");
        }

        log.info("Opening feedback receiver client");

        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_005: [The function shall call open() on the member AMQPReceive object]
        this.amqpReceive.open();

        log.info("Opened feedback receiver client");
    }

    /**
     * Close AmqpReceive object
     *
     * @throws IOException This exception is thrown if the input AmqpReceive object is null
     */
    public void close() throws IOException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_006: [The function shall throw IOException if the member AMQPReceive object has not been initialized]
        if (this.amqpReceive == null)
        {
            throw new IOException("AMQP receiver is not initialized");
        }

        log.info("Closing feedback receiver client");

        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_007: [The function shall call close() on the member AMQPReceive object]
        this.amqpReceive.close();

        log.info("Closed feedback receiver client");
    }

    /**
     * Receive FeedbackBatch with default timeout
     *
     * This function is synchronized internally so that only one receive operation is allowed at a time.
     * In order to do more receive operations at a time, you will need to instantiate another FeedbackReceiver instance.
     *
     * @return The received FeedbackBatch object
     * @throws IOException This exception is thrown if the input AmqpReceive object is null
     * @throws InterruptedException This exception is thrown if the receive process has been interrupted
     */
    public FeedbackBatch receive() throws IOException, InterruptedException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_008: [The function shall call receive(long timeoutMs) function with the default timeout]
        return receive(DEFAULT_TIMEOUT_MS);
    }

    /**
     * Receive FeedbackBatch with specific timeout
     *
     * This function is synchronized internally so that only one receive operation is allowed at a time.
     * In order to do more receive operations at a time, you will need to instantiate another FeedbackReceiver instance.
     *
     * @param timeoutMs The timeout in milliseconds
     * @return The received FeedbackBatch object
     * @throws IOException This exception is thrown if the input AmqpReceive object is null
     * @throws InterruptedException This exception is thrown if the receive process has been interrupted
     */
    public FeedbackBatch receive(long timeoutMs) throws IOException, InterruptedException
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_009: [The function shall throw IOException if the member AMQPReceive object has not been initialized]
        if (this.amqpReceive == null)
        {
            throw new IOException("AMQP receiver is not initialized");
        }
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_010: [The function shall call receive() on the member AMQPReceive object and return with the result]
        return this.amqpReceive.receive(timeoutMs);
    }

    /**
     * Async wrapper for open() operation
     *
     * @return The future object for the requested operation
     */
    @Override
    public CompletableFuture<Void> openAsync()
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_011: [The function shall create an async wrapper around the open() function call]
        final CompletableFuture<Void> future = new CompletableFuture<>();
        executor.submit(() -> {
            try
            {
                open();
                future.complete(null);
            } catch (IOException e)
            {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    /**
     * Async wrapper for close() operation
     *
     * @return The future object for the requested operation
     */
    @Override
    public CompletableFuture<Void> closeAsync()
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_012: [The function shall create an async wrapper around the close() function call]
        final CompletableFuture<Void> future = new CompletableFuture<>();
        executor.submit(() -> {
            try
            {
                close();
                future.complete(null);
            } catch (IOException e)
            {
                future.completeExceptionally(e);
            }
        });
        return future;
    }

    /**
     * Async wrapper for receive() operation with default timeout
     *
     * @return The future object for the requested operation
     */
    @Override
    public CompletableFuture<FeedbackBatch> receiveAsync()
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_013: [The function shall create an async wrapper around the receive() function call]
        return receiveAsync(DEFAULT_TIMEOUT_MS);
    }

    /**
     * Async wrapper for receive() operation with specific timeout
     *
     * @return The future object for the requested operation
     */
    @Override
    public CompletableFuture<FeedbackBatch> receiveAsync(long timeoutMs)
    {
        // Codes_SRS_SERVICE_SDK_JAVA_FEEDBACKRECEIVER_12_014: [The function shall create an async wrapper around the receive(long timeoutMs) function call]
        final CompletableFuture<FeedbackBatch> future = new CompletableFuture<>();
        executor.submit(() -> {
        try
        {
            FeedbackBatch responseFeedbackBatch = receive(timeoutMs);
            future.complete(responseFeedbackBatch);
        } catch (IOException e)
        {
            future.completeExceptionally(e);
        } catch (InterruptedException e)
        {
            future.completeExceptionally(e);
        }
        });
        return future;
    }
}
