package fr.damienraymond.cqrs.core.web.context.communication

import java.net.URL

import com.google.inject.{ImplementedBy, Inject}
import fr.damienraymond.cqrs.example.module.ServerConfiguration
import play.api.libs.json.{Format, JsValue, Json}
import play.api.libs.ws.{WSClient, WSRequest}
import play.api.mvc.Call

import scala.concurrent.{ExecutionContext, Future}

@ImplementedBy(classOf[ServiceCallFactoryImplementation])
trait ServiceCallFactory {

  def get(path: String): ServiceCall

  def delete(path: String): ServiceCall

  def post[BODY](path: String, body: BODY)(implicit format: Format[BODY]): ServiceCall

  def put[BODY](path: String, body: BODY)(implicit format: Format[BODY]): ServiceCall

}



class ServiceCallFactoryImplementation @Inject()(ws: WSClient, serverConfiguration: ServerConfiguration)(implicit ec: ExecutionContext) extends ServiceCallFactory {

  def get(path: String): ServiceCall =
    new ServiceCallImplementation("GET", path, wsurl(path).get)

  def delete(path: String): ServiceCall =
    new ServiceCallImplementation("DELETE", path, wsurl(path).delete)

  def post[BODY](path: String, body: BODY)(implicit format: Format[BODY]): ServiceCall =
    new ServiceCallImplementation("POST", path, wsurl(path).post(serialize(body)))

  def put[BODY](path: String, body: BODY)(implicit format: Format[BODY]): ServiceCall =
    new ServiceCallImplementation("PUT", path, wsurl(path).put(serialize(body)))

  private def wsurl(path: String) = ws.url(pathHostnamePrefixer(path))

  private def pathHostnamePrefixer(path: String) = new URL(new URL(serverConfiguration.hostname), path).toString

  private def serialize[BODY](body: BODY)(implicit format: Format[BODY]) = Json.toJson(body)
}


@ImplementedBy(classOf[ServiceCall])
trait ServiceCall {

  def text: Future[String]

  def as[RESPONSE](implicit responseFormat: Format[RESPONSE]): Future[RESPONSE]

}

private[communication] class ServiceCallImplementation(method: String, path: String, futureRes: Future[WSRequest#Response])(implicit ec: ExecutionContext) extends ServiceCall {

  private def extract[RESPONSE](f: WSRequest#Response => RESPONSE)(response: WSRequest#Response): Future[RESPONSE] =
    if (Range.apply(200, 300).contains(response.status)) Future.successful(f(response))
    else Future.failed(ServiceCallException(method, path, response.status, response.statusText))

  def text: Future[String] = futureRes.flatMap(extract(_.body))

  def as[RESPONSE](implicit responseFormat: Format[RESPONSE]): Future[RESPONSE] =
    futureRes.flatMap(extract(_.json.as[RESPONSE]))

}
