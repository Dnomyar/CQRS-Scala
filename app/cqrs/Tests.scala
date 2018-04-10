package fr.damienraymond.cqrs

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.infrastructure.event.EventBusMiddleware
import fr.damienraymond.cqrs.example.commands.{BuyProducts, BuyProductsHandler}
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepository, StoreRepository}

import scala.concurrent.ExecutionContext

class Tests @Inject()(buyProductsHandler: BuyProductsHandler,
                      eventBusMiddleware: EventBusMiddleware)(implicit ec: ExecutionContext) {


  val buyProducts = BuyProducts(StoreRepository.storeId, ProductRepository.productId1)

//  val commandBus = commandBusFactory.create(List(eventBusMiddleware))
//
//  val res = commandBus.dispatch[BuyProducts, Unit](buyProducts)
//
//  println()
//  println()
//  println(s"RES : $res")
//  res.map(r => println(s"RES : $r"))


}
