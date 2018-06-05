# CQ(R)S building block in Scala
This project provides some key block to build an architecture of bounded contexts. 


## Why this project ?
### DDD provides efficient practices to understand and communicate on business needs.
Bringing quality in a software has always been one of my concerns. 

Few weeks ago, I discovered Domain-Driven-Design. DDD is an approach (rather than an architecture) that helps to focus business needs. 


DDD is often known to express complex business needs. It indeed helps to build and maintain very scalable architectures that are base on concepts such as : `Command`, `Query`, `Event`, `Business Rule`.


DDD also brings a quite an interesting workshop to help the understanding of business needs : the Event Storming. In practice, this workshop is held with the client (who knows perfectly his business) and with the developers. The result of this workshop gathers all `Commands`, `Queries`, `Events` and `Business Rules` representing the business.

Then `Commands`, `Queries`, `Events` and `Business Rules` are translated into executable code. The goal of this project is to provide some building blocks to manage life cycle of `Commands`, `Queries` and `Events` in the app.

### Only for big projects ?

DDD brings a lot of concept to build very scalable architectures that fit well for "big" projects. 

However, I think that DDD can also be used I "small" or "medium" projects that don't need to scale. DDD powerful concepts help to build a better app even if the app is small. 

## How to use this project ?

### Step 1 : Event Storming
### Step 2 : Build the Aggregates (and test them)
### Step 3 : Add Commands, Queries, Event and business rules (and test them)


## Credits
Inspired by the great work available here https://github.com/arpinum/alexandria-api
