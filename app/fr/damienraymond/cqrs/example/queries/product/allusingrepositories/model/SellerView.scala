package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model


import java.util.UUID

import play.api.libs.json._


case class SellerView(id: UUID,
                      name: String,
                      sellerPictUrl: String)



object SellerView {
  implicit val format = Json.format[SellerView]
}