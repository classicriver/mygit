package com.tw.mqtt;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.activemq.apollo.broker.Broker;
import org.apache.activemq.apollo.broker.security.Authorizer;
import org.apache.activemq.apollo.broker.security.SecuredResource;
import org.apache.activemq.apollo.broker.security.SecurityContext;
import org.apache.activemq.apollo.dto.AcceptingConnectorDTO;
import org.apache.activemq.apollo.dto.AccessRuleDTO;
import org.apache.activemq.apollo.dto.AuthenticationDTO;
import org.apache.activemq.apollo.dto.BrokerDTO;
import org.apache.activemq.apollo.dto.TopicDTO;
import org.apache.activemq.apollo.dto.VirtualHostDTO;
import org.apache.activemq.apollo.dto.WebAdminDTO;
import org.apache.activemq.apollo.mqtt.dto.MqttDTO;

import com.tw.log.LogFactory;

public class AppConfig {
	public void broker() throws Exception {
        final Broker broker = new Broker();
    
        // Configure STOMP over WebSockects connector
        final AcceptingConnectorDTO mqtt = new AcceptingConnectorDTO();
        mqtt.id = "tcp";
        mqtt.bind = "tcp://localhost:61614";  
        mqtt.protocols.add( new MqttDTO() );

        // Create a topic with name 'test'
        final TopicDTO topic = new TopicDTO();
        topic.id = "Pubdata";
  
        // Create virtual host (based on localhost)
        final VirtualHostDTO host = new VirtualHostDTO();
        host.id = "localhost";  
        host.topics.add( topic );
        host.host_names.add( "localhost" );
        host.host_names.add( "127.0.0.1" );
        host.auto_create_destinations = false;
  
        // Create a web admin UI (REST) accessible at: http://localhost:61680/api/index.html#!/ 
        final WebAdminDTO webadmin = new WebAdminDTO();
        webadmin.bind = "http://localhost:61680";

        // Create JMX instrumentation 
        /*final JmxDTO jmxService = new JmxDTO();
        jmxService.enabled = true;*/
  
        // Finally, glue all together inside broker configuration
        final BrokerDTO config = new BrokerDTO();
        config.connectors.add( mqtt );
        config.virtual_hosts.add( host );
        config.web_admins.add( webadmin );
        //config.access_rules.add(e)
        AccessRuleDTO adto = new AccessRuleDTO();
        //adto
        //config.services.add( jmxService );
       // config.authentication
        AuthenticationDTO audto = new AuthenticationDTO();
        //audto.
        broker.setConfig( config );
        broker.setTmp( new File( System.getProperty( "java.io.tmpdir" ) ) );
       /*
        Authorizer au = new Authorizer() {
			
			@Override
			public Boolean can(SecurityContext arg0, String arg1, SecuredResource arg2) {
				// TODO Auto-generated method stub
				return null;
			}
		};
		broker.authorizer_$eq(au);*/
        broker.start( new Runnable() {   
            @Override
            public void run() {  
                System.out.println("The broker has been started started.");
            }
        } );
        registerShutDownHook();
		try {
			System.in.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.exit(0);
    }
	
	// 注册关机hook,清理线程
	private void registerShutDownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				LogFactory.getLogger().info("shutdown..");
			}
		}));
	}
	public static void main(String[] args) {
		try {
			
			new AppConfig().broker();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
