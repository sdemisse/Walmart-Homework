# Walmart-Drone Delivery

### Building, Testing and Running

* This command will build and execute all the unit test
 	`mvn clean install`
 
* To run 
`java  -jar target/drone-delivery-1.0.0.jar <Input file path> `

### Assumption

  1. The sum of the time taken for all orders to deliver is always less than or equal to 16 hour
  2. The drone know the distance after the order received.
  3. All the input will be on the correct format
  
### Calculate score for promoter and detractor .
  	* Order waiting time < 2 hour ==> Promoter
  	* Order waiting time < 4 hour and >= 2==> Neutral
  	* Order waiting time >=4 ==> Detractor
 
