package fr.damienraymond.cqrs.example.queries.product.allusingrepositories.handlers

import java.util.UUID

import fr.damienraymond.cqrs.example.model.product.Product
import fr.damienraymond.cqrs.example.model.seller.Seller
import fr.damienraymond.cqrs.example.queries.product.allusingrepositories.model.{ProductView, SellerView}

class ProductToProductView {

  def toProductView(sellers: List[Seller])(product: Product): Option[ProductView] = {
    sellers.find(_.products.contains(product.id)).map { seller =>
      ProductView(
        product.id,
        product.name,
        product.price.value,
        product.price.value * 1.2,
        product.stock.numberOfAvailableProducts,
        SellerView(
          seller.id,
          seller.name,
          getSellerPictUrl(seller.id)
        )
      )
    }
  }


  def getSellerPictUrl(id: UUID) = s"http://localhost:9000/img/$id"


}
