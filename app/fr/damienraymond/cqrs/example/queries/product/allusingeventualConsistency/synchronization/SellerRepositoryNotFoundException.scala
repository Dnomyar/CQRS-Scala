package fr.damienraymond.cqrs.example.queries.product.allusingeventualConsistency.synchronization

import java.util.UUID

class SellerRepositoryNotFoundException(productId: UUID)
  extends Exception(s"Cannot find seller for product $productId")
