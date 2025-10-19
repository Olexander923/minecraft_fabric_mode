package shadrin.dev.client.screen;

import mymod.messages.MessageOuterClass;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import shadrin.dev.network.MessagePayload;

import java.util.UUID;

public class MessageScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget textField;

    private ButtonWidget sendButton;

    public MessageScreen(Screen parent) {
        super(Text.literal("Send message"));
        this.parent = parent;
    }

    @Override
    public void init(){
        int fieldWidth = 200;
        int fieldHeight = 20;
        int xField = (this.width - fieldWidth) / 2;
        int yField = this.height / 2 - 10;
        textField = new TextFieldWidget(this.textRenderer,xField,yField
        ,fieldWidth,fieldHeight,Text.literal("Message"));

        textField.setMaxLength(200);
        textField.setEditable(true);
        textField.setText(" ");
        textField.setFocused(true);
        this.addDrawableChild(textField);

       // кнопка Send
        int btnWidth = 100;
        int btnHeight = 20;
        int xBtn = (this.width - btnWidth) / 2;
        int yBtn = yField + 25;

        sendButton = ButtonWidget.builder(Text.literal("send"),
                b -> {
                    String text = textField.getText().trim();
                    if (text.isEmpty()) return;
                    try {
                        byte[] protoBytes = MessageOuterClass.Message.newBuilder()
                                .setText(text)
                                .build()
                                .toByteArray();
                        UUID playerId = MinecraftClient.getInstance().player.getUuid();

                        ClientPlayNetworking.send(new MessagePayload(playerId,protoBytes));
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                    this.close();          // закрываем экран
                })
                .dimensions(xBtn, yBtn, btnWidth, btnHeight)
                .build();
                this.addDrawableChild(sendButton);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta); // фон
        super.render(context, mouseX, mouseY, delta);          // виджеты
        // заголовок по центру сверху
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, 0xFFFFFF);
    }
    @Override
    public void close() {
        if (client != null) client.setScreen(parent); // вернуться назад
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            // обработка Enter для отправки сообщения
            if (textField.isFocused()) {
                sendButton.onPress();
                return true;
            }
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}
