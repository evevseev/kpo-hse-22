package agents;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class ProductAgent extends BasicAgent {
    // Представляет некоторый объем продукта, необходимый для приготовления определенного блюда / напитка.
    // Создается агентом склада (или по его «просьбе» управляющим агентом) при формировании заказа, содержащего блюдо / напиток,
    // для приготовления которого необходим этот продукт. При создании агента продукта необходимый объем продукта, физически находящегося на складе,
    // резервируется. Под каждый экземпляр блюда / напитка определенного заказа создается свой агент продукта. Когда блюдо / напиток будет приготовлено,
    // агент продукта уничтожается агентом склада, а ранее зарезервированный и физически потраченный поваром объем этого продукта списывается со склада.

    // Это агенты, представляющие определенные типы ресурсов – ингредиенты для блюд и напитков (мука, кофе и т. д.), могут предоставлять информацию о зарезервированных ресурсах.
    // Типовое поведение:
    // 1. Зарезервировать указанный объем ресурса, который он представляет (например, 0.3 кг картофеля).
    // 2. Отменить резервирование ресурса.
    // 3. «Захватить» с помощью агента склада указанный объем ресурса со склада для использования в процессе приготовления блюда / напитка.
    protected String productName;
    protected double productQnt;

    @Override
    protected void setup() {
        super.setup();

        if (startupArgs != null && startupArgs.length >= 2) {
            // TODO: add type checking
            productName = (String) startupArgs[0];
            productQnt = (double) startupArgs[1];
            addBehaviour(new QntRequestServer());
        } else {
            // TODO: use logging
            System.out.println("[" + getAID().getName() + "]: Product name and qnt were not provided");
            doDelete();
        }
    }

    private class QntRequestServer extends CyclicBehaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);

            ACLMessage msg = myAgent.receive(mt);
            if (msg != null) {
                ACLMessage reply = msg.createReply();

                if (productQnt > 0) {
                    reply.setPerformative(ACLMessage.PROPOSE);
                    reply.setContent(String.valueOf(productQnt));
                } else {
                    reply.setPerformative(ACLMessage.REFUSE);
                    reply.setContent("not-available");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}
