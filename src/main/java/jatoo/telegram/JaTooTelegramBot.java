/*
 * Copyright (C) Cristian Sulea ( http://cristian.sulea.net )
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
 * @version 1.1, October 18, 2016
 */
public abstract class JaTooTelegramBot extends TelegramLongPollingCommandBot {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooTelegramBot.class);

  private final String botUsername;
  private final String botToken;

  public JaTooTelegramBot(String username, String token, BotCommand... commands) {

    this.botUsername = username;
    this.botToken = token;

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

  @Override
  public String getBotUsername() {
    return botUsername;
  }

  @Override
  public String getBotToken() {
    return botToken;
  }

  protected abstract String getWelcomeMessage();

  protected abstract String getHelpMessage();

}
