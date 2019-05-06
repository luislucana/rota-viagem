ROTAS VIAGEM APPLICATION
==================================

## Instruções para executar esta aplicação

Para executar esta API, é necessário ter instalado:

* Java 8 (jre instalado)

Baixar o arquivo JAR (já gerado) [rotas.jar] (https://github.com/luislucana/rota-viagem/blob/master/rotas.jar)

e executar a linha de comando
  > java -jar rotas.jar [NOME DO ARQUIVO CONTENDO AS ROTAS]
  
## Instruções para as chamadas REST

#### Para criar uma rota, deve ser enviada uma requisição POST, como o exemplo abaixo:

curl -X POST \
  http://localhost:8080/rota \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/json' \
  -H 'Host: localhost:8080' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: 56' \
  -d '{
	"origem": "CGN",
	"destino": "VCP",
	"preco": "123"
}'

#### Para consultar a melhor rota, deve ser enviada uma requisição GET, como o exemplo abaixo:

curl -X GET \
  'http://localhost:8080/rota?origem=GRU&destino=CDG' \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Host: localhost:8080' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache'
