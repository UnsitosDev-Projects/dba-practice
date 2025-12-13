const Eureka = require('eureka-js-client').Eureka;

const eurekaConfig = {
  instance: {
    app: 'dba-courses',
    hostName: 'localhost',
    ipAddr: '127.0.0.1',
    port: {
      '$': 8002,
      '@enabled': true,
    },
    vipAddress: 'dba-courses',
    dataCenterInfo: {
      '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
      name: 'MyOwn',
    },
  },
  eureka: {
    host: process.env.EUREKA_HOST || 'localhost',
    port: process.env.EUREKA_PORT || 8761,
    servicePath: '/eureka/apps/',
    maxRetries: 3,
    requestRetryDelay: 500,
  },
};

const client = new Eureka(eurekaConfig);

const startEureka = () => {
  if (process.env.ENABLE_EUREKA === 'true') {
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
  if (process.env.ENABLE_EUREKA === 'true') {
    client.stop();
    console.log('Microservicio dba-courses des-registrado de Eureka');
  }
};

module.exports = { startEureka, stopEureka };
