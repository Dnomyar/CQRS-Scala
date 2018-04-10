package fr.damienraymond.cqrs.example.model.seller

import java.util.UUID

import fr.damienraymond.cqrs.example.model.product.ProductId

case class Seller(id: SellerId,
                  name: String,
                  products: Set[ProductId])
