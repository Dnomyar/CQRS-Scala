package fr.damienraymond.cqrs

import java.util.UUID

import com.google.inject.Inject
import fr.damienraymond.cqrs.core.infrastructure.bus.{CommandBus, CommandBusFactory}
import fr.damienraymond.cqrs.core.infrastructure.event.EventBusMiddleware
import fr.damienraymond.cqrs.example.commands.{BuyProducts, BuyProductsHandler, PutArticleOnSale}

class Tests @Inject()(buyProductsHandler: BuyProductsHandler,
                      eventBusMiddleware: EventBusMiddleware,
                      commandBusFactory: CommandBusFactory) {

  val storeId = UUID.randomUUID()

  val productId = UUID.randomUUID()


  val buyProducts = BuyProducts(storeId, productId)

  val commandBus = commandBusFactory.create(List(eventBusMiddleware))

  commandBus.dispatch[BuyProducts, Unit](buyProducts)


}
