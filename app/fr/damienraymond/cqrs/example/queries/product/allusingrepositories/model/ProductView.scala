package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model

import java.util.UUID
import play.api.libs.json._




case class ProductView(id: UUID,
                       name: String,
                       priceDutyFree: Double,
                       vatPrice: Double,
                       numberOfProductAvailable: Long,
                       seller: SellerView
                      )


object ProductView {
  implicit val format = Json.format[ProductView]
}