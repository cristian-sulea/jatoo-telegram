package jatoo.telegram;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Chat;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.AbsSender;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * A very simple implementation of the <code>/start</code> {@link BotCommand}
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, October 12, 2016
 */
public class JaTooTelegramBotStartCommand extends BotCommand {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooTelegramBotStartCommand.class);

  private final String welcomeMessage;

  public JaTooTelegramBotStartCommand(String welcomeMessage) {
    super("start", "With this command you can start the Bot.");
    this.welcomeMessage = welcomeMessage;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

    String userName = chat.getUserName();
    if (userName == null || userName.isEmpty()) {
      userName = user.getFirstName() + " " + user.getLastName();
    }
    userName = userName.trim();

    StringBuilder buffer = new StringBuilder();

    buffer.append("Welcome ").append(userName).append("\n");
    buffer.append(welcomeMessage);

    SendMessage response = new SendMessage();
    response.setChatId(chat.getId().toString());
    response.setText(buffer.toString());

    try {
      absSender.sendMessage(response);
    } catch (TelegramApiException e) {
      logger.error("failed to send the response to the start command", e);
    }
  }

}