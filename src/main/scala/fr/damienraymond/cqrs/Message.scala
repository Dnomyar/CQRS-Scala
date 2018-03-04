package fr.damienraymond.cqrs

trait Message[+RETURN_TYPE]

trait Command[+RETURN_TYPE] extends Message[RETURN_TYPE]

trait Query[+RETURN_TYPE] extends Message[RETURN_TYPE]
