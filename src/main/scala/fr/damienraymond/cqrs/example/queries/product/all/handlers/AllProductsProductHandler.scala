package fr.damienraymond.cqrs.example.queries.product.all.handlers

import fr.damienraymond.cqrs.core.{Query, QueryHandler}

case class AllProducts() extends Query[Product]

class AllProductsProductHandler extends QueryHandler[AllProducts, ] {

}
