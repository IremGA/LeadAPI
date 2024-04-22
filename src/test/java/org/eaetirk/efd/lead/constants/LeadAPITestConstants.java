package org.eaetirk.efd.lead.constants;

public class LeadAPITestConstants {

    public static final String REQUEST_CREATE_JSON = """
            {
                "comment": "new About to be Created",
                "e_mail": "iremgamzeli@gmail.com",
                "first_name": "Irem",
                "last_name": "Aktas",
                "leadDeviceSpecificationList": [
                    {
                        "comment": "Leather",
                        "leadDeviceSpecificationType": "TRIM",
                        "specificationPriority": "MUST_HAVE"
                    }
                ],
                "leadOfferList": [
                    {
                        "currency": "EUR",
                        "car": {
                            "brand": "BMW",
                            "creation_year": "2022",
                            "location": "Jankomir",
                            "model": "BMW_X5",
                            "style": "SUV",
                            "kilometer": 2000
                        },
                        "offerStatus": "ACTIVE",
                        "priceAmount": 55000.00
                    }
                ],
                "leadStatus": "ACTIVE",
                "occupation": "Product Infrastructure Planner",
                "paymentPlan": "CASH"
            }""";
    public static final String REQUEST_UPDATE_LEAD = """
            {
                "comment": "BBB",
                "respondBefore": "2024-04-23T14:50:04.437216Z",
                "leadOfferList": [
                    {   "id": 1,
                        "car": {
                            "model": "BMW_X4"
                        }
                    }
                ],
                "leadDeviceSpecificationList": [
                        {
                            "comment": "Leather, color black",
                            "id": 1
                        }
                    ]
            }
            """;

    public static final String REQUEST_UPDATE_LEAD_SHORT = """
            {
                "comment": "BBB"

            }
            """;
}