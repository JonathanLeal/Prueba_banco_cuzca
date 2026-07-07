Bienvenidos a la solucion del sistema digital para administrar de manera inteligente la reserva de salas, oficinas etc.
El sistema está diseñado para ser seguro, rápido y capaz de soportar fallos de servicios externos sin interrumpir la experiencia del usuario.

## Requisitos Previos
- Tener instalado **Docker Desktop** en su equipo.

## Pasos para Ejecutar
1. Abra una terminal (consola) y navegue hasta la carpeta raíz donde se encuentran los archivos de este proyecto.
2. Ejecute el siguiente comando para compilar el código Java y encender la base de datos automáticamente:
   ```bash
   docker-compose up --build 
   
Una vez que el proyecto esté corriendo, no necesita usar herramientas externas para ver cómo funciona ya que se a integrado Swagger
http://localhost:8084/swagger-ui/index.html

## Librerias utilizadas: 
- Spring Security & JWT: evita que usuarios no registrados accedan al sistema.
 
- Resilience4j: Funciona como un fusible eléctrico para emergencias. Si el servicio externo que procesa los cobros con tarjeta se cae o responde lento, este componente lo detecta y frena las llamadas al servicio roto y guarda la reserva como pago pendiente en lugar de mostrar un error en la pantalla del cliente.

- Spring Cache: funciona como optimizador de velocidad un ejemplo de uso es cuando un administrador pide un informe pesado el sistema guarda los datos en memoria y las siguientes veces que se piden los reportes se entrega al instante.

- Lombok: ayuda a mantener el codigo limpio de lineas repetitivas.

## Utilidades
- Simulación de Correos: Las notificaciones se disparan de forma asíncrona simulando la salida en la consola del servidor en lugar de conectar un servidor de correos real.

- Simulación de Pagos: La pasarela bancaria externa se modeló mediante un cliente simulado con lógica aleatoria y controlada por Circuit Breaker para certificar la tolerancia a fallos del software sin requerir contratos o credenciales de pasarelas reales (como Stripe o PayPal).

## con mas tiempo:
me hubiese encantado aplicar una macanica de reintentos.