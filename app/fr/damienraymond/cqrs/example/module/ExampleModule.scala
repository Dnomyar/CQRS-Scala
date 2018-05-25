package fr.damienraymond.cqrs.example.module

import com.google.inject.Provides
import fr.damienraymond.cqrs.example.infrastructure.persistence.{ProductRepositoryImplementation, SellerRepositoryImplementation}
import fr.damienraymond.cqrs.example.model.product.ProductRepository
import fr.damienraymond.cqrs.example.model.seller.SellerRepository
import fr.damienraymond.cqrs.example.web.BenchActor
import net.codingwell.scalaguice.ScalaModule
import play.api.Configuration
import play.api.libs.concurrent.AkkaGuiceSupport

class ExampleModule extends ScalaModule with AkkaGuiceSupport {
  override def configure(): Unit = {
    bind[ProductRepository].to[ProductRepositoryImplementation].asEagerSingleton()
    bind[SellerRepository].to[SellerRepositoryImplementation].asEagerSingleton()
    bindActor[BenchActor]("bench-actor")
  }


  @Provides
  def providesServerConfiguration(configuration: Configuration): ServerConfiguration =
    ServerConfiguration(configuration.get[String]("server.hostname"))


}
