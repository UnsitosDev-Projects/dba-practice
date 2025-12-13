const Eureka = require('eureka-js-client').Eureka;

// Configuración local
const APP_NAME = 'dba-courses';
const APP_PORT = parseInt(process.env.APP_PORT || '8002');
const EUREKA_HOST = process.env.EUREKA_HOST || 'localhost';
const EUREKA_PORT = parseInt(process.env.EUREKA_PORT || '8761');
const ENABLE_EUREKA = process.env.ENABLE_EUREKA !== 'false';

const eurekaConfig = {
  instance: {
    app: APP_NAME,
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    port: {
      '$': APP_PORT,
      '@enabled': true,
    },
    vipAddress: APP_NAME,
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: EUREKA_HOST,
    port: EUREKA_PORT,
    servicePath: '/eureka/apps/',
    maxRetries: 3,
    requestRetryDelay: 500,
  },
};

const client = new Eureka(eurekaConfig);

const startEureka = () => {
  if (ENABLE_EUREKA) {
    client.start((error) => {
      if (error) {
        console.error('Error al conectar con Eureka:', error);
      } else {
        console.log('Microservicio dba-courses registrado en Eureka');
      }
    });
  } else {
    console.log('Microservicio dba-courses iniciado sin Eureka');
  }
};

const stopEureka = () => {
  if (ENABLE_EUREKA) {
    client.stop();
    console.log('Microservicio dba-courses des-registrado de Eureka');
  }
};

module.exports = { startEureka, stopEureka, APP_PORT };
