import org.springframework.cloud.contract.spec.Contract
Contract.make {
    description "should return 201 when creating new Livraison"
    request{
        method POST()
        url("/api/livraison") {
            queryParameters {
                parameter("noCommande", $(anyNumber()))
            }
        }
    }
    response {
        status 201
    }
}

