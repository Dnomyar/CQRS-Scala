//package fr.damienraymond.cqrs.example.model.register
//
//import java.util.UUID
//
//import fr.damienraymond.cqrs.core.entity.BaseAggregate
//import fr.damienraymond.cqrs.example.model.product.events.errors.ProductNotAvailable
//
//case class Register(storeId: UUID, productId: UUID, numberOfAvailableArticle: Long) {
//
//  private[model] def removeProduct(numberOfProductToRemove: Long): Either[ProductNotAvailable, Register] = {
//    val newNumberOfProduct = numberOfAvailableArticle - numberOfProductToRemove
//    if(newNumberOfProduct >= 0) Right(copy(numberOfAvailableArticle = newNumberOfProduct))
//    else Left(ProductNotAvailable(storeId, productId))
//  }
//
//}
