import urllib.request
import json
import random
import string    


myurl = "http://localhost:8080/products/addProduct"
req = urllib.request.Request(myurl)
req.add_header('Content-Type', 'application/json; charset=utf-8')


def addProduct(name):
  body = {
      "name":name,
      "category":"Patiserie",
      "price":2.5,
      "publishedOn":"1621728276",
      "isNew":random.choice([0,1]),
      "isInStock":random.choice([0,1]),
      "isOnSale":random.choice([0,1])
  }  
  jsondata = json.dumps(body)
  jsondataasbytes = jsondata.encode('utf-8')   # needs to be bytes
  req.add_header('Content-Length', len(jsondataasbytes))
  response = urllib.request.urlopen(req, jsondataasbytes)

for i in range(150):
    addProduct(''.join(random.choices(string.ascii_uppercase + string.digits, k=5)))