package fr.damienraymond.cqrs.example.model.seller

import fr.damienraymond.cqrs.core.persistence.UUIDRepository

trait SellerRepository extends UUIDRepository[Seller]