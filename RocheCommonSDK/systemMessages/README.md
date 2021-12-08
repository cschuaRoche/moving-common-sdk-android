Roche systemMessages library
========
This is the systemMessages library which provides the functionality of retrieving system messages from BE.

How to use
----------
How to get system messages based on message type, appOrSamdId, appOrSamdVersion and country (optional)
----------
    ```
    val systemMessages = SystemMessages.getSystemMessages(
                    context,
                    baseUrl,
                    messageTypeList,
                    appOrSamdId,
                    appOrSamdVersion,
                    country
                )
    ```
How to dismiss the message for the given resource id
----------
    ```
    SystemMessages.dismissMessage(context, resourceId)
    ```