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
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

/**
 * A collection of utility methods to ease the work with work with JaToo
 * Telegram library.
 * 
 * @author <a href="http://cristian.sulea.net" rel="author">Cristian Sulea</a>
 * @version 1.0, October 17, 2016
 */
public abstract class JaTooTelegramUtils {

  /** the logger */
  private static final Log logger = LogFactory.getLog(JaTooTelegramUtils.class);

  private static TelegramBotsApi telegramBotsApi;

  public static void registerBot(JaTooTelegramBot bot) throws Throwable {

    synchronized (JaTooTelegramUtils.class) {
      if (telegramBotsApi == null) {
        synchronized (JaTooTelegramUtils.class) {
          telegramBotsApi = new TelegramBotsApi();
        }
      }
    }

    try {
      telegramBotsApi.registerBot(bot);
    }

    catch (TelegramApiRequestException e) {
      logger.error("error registering bot - " + bot.getBotUsername(), e);
      throw e;
    }
  }

}
