package fr.damienraymond.cqrs.example.queries.product.all.model

import java.util.UUID

case class SellerView(id: String,
                      name: String,
                      sellerPictUrl: String)

case class ProductView(id: UUID,
                       name: String,
                       priceDutyFree: Double,
                       vatPrice: Double,
                       numberOfProductAvailable: Int,
                       storeId: UUID,
                       seller: SellerView
                      )


object ProductView {
  implicit val format = Json.format[ProductView]
}