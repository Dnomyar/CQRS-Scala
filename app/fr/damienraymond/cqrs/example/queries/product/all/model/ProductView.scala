package fr.damienraymond.cqrs.example.queries.product.all.model

import java.util.UUID
import play.api.libs.json._




case class
ProductView(id: UUID,
                       name: String,
                       priceDutyFree: Double,
                       vatPrice: Double,
                       numberOfProductAvailable: Long,
                       storeId: UUID,
                       seller: SellerView
                      )


object ProductView {
  implicit val format = Json.format[ProductView]
}