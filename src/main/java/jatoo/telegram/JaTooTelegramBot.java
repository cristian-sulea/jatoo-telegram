package jatoo.telegram;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.bots.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.bots.commands.BotCommand;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * A skeletal implementation of the {@link TelegramLongPollingBot} bot.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, October 12, 2016
 */
public abstract class JaTooTelegramBot extends TelegramLongPollingCommandBot {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooTelegramBot.class);

  public JaTooTelegramBot(BotCommand... commands) {

    register(new JaTooTelegramBotStartCommand(getWelcomeMessage()));

    if (commands != null) {
      for (BotCommand command : commands) {
        register(command);
      }
    }
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    if (update.hasMessage()) {
      Message message = update.getMessage();
      if (message.hasText()) {

        SendMessage response = new SendMessage();
        response.setChatId(message.getChatId().toString());
        response.setText(getHelpMessage());

        try {
          sendMessage(response);
        } catch (TelegramApiException e) {
          logger.error("failed to send the non command response", e);
        }
      }
    }
  }

  protected abstract String getWelcomeMessage();

  protected abstract String getHelpMessage();

}
