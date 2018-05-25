package fr.damienraymond.cqrs.example.model.product

import fr.damienraymond.cqrs.core.persistence.UUIDRepository

trait ProductRepository extends UUIDRepository[Product]
