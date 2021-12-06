package com.roche.ssg.pushnotification.mockresponse

object DeregisterMockResponse {
    operator fun invoke(): String = "{\n" +
            "  \"meta\": {\n" +
            "    \"transactionId\": \"3c76785f-7ef7-4f91-a40b-aedafdea17b9\",\n" +
            "    \"requestTime\": 1634710188\n" +
            "  },\n" +
            "  \"userId\": \"cd8cccfc-f544-41c7-9ada-ea72a2636246\",\n" +
            "  \"successEndpoints\": [\n" +
            "    \"06d07b3b-c2c9-4744-8cfa-7d52b0a32fcd\",\n" +
            "    \"812715a2-dc77-452f-bacb-2b8e0135c9fc\"\n" +
            "  ],\n" +
            "  \"failureEndpoints\": [\n" +
            "    \"06d07b3b-c2c9-4744-8cfa-7d52b0a32fcd\",\n" +
            "    \"812715a2-dc77-452f-bacb-2b8e0135c9fc\"\n" +
            "  ]\n" +
            "}"
}