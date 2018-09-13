/*
 * This file is part of Debuggery.
 *
 * Debuggery is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Debuggery is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Debuggery.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.destroystokyo.debuggery.reflection.types;

import com.destroystokyo.debuggery.reflection.types.handlers.base.Handler;
import com.destroystokyo.debuggery.reflection.types.handlers.input.*;
import com.destroystokyo.debuggery.reflection.types.handlers.output.*;
import com.destroystokyo.debuggery.util.DebugLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Bootstraps all handler classes into the TypeHandler system
 */
class Bootstrap {

    /**
     * Registers all handler classes
     *
     * @param typeHandler {@link TypeHandler} instance to bootstrap
     */
    static void init(TypeHandler typeHandler) {
        DebugLogger.debugLn("Begin TypeHandler bootstrap");
        List<Handler> registration = new ArrayList<>();

        //
        // Input Handlers
        //

        new IPrimitivesHandler(typeHandler); // Special cased for multi-registration

        // order can matter here
        registration.add(new IBukkitClassHandler());
        registration.add(new IBukkitClassesHandler());
        registration.add(new IDifficultyHandler());
        registration.add(new IEntityHandler());
        registration.add(new IGameModeHandler());
        registration.add(new IItemStackHandler());
        registration.add(new IItemStacksHandler());
        registration.add(new ILocationHandler());
        registration.add(new IMaterialDataHandler());
        registration.add(new IMaterialHandler());
        registration.add(new IStringHandler());
        registration.add(new IUUIDHandler());
        // register polymorphics last
        registration.add(new IBlockDataHandler());
        registration.add(new ICollectionHandler());
        registration.add(new IEnumHandler());

        //
        // Output Handlers
        //

        new OArrayHandler(typeHandler); // Special cased for multi-registration

        // order can matter here
        registration.add(new OBlockStateHandler());
        registration.add(new OCollectionHandler());
        registration.add(new OEntityHandler()); // above CommandSender
        registration.add(new OCommandSender());
        registration.add(new OHelpMapHandler());
        registration.add(new OInventoryHandler());
        registration.add(new OMapHandler());
        registration.add(new OMessengerHandler());
        registration.add(new OOfflinePlayerHandler());
        registration.add(new OStringHandler());
        registration.add(new OWorldBorderHandler());

        for (Handler handler : registration) {
            if (!typeHandler.registerHandler(handler)) {
                throw new IllegalArgumentException("Unable to register " + handler);
            }
        }

        DebugLogger.debugLn("End TypeHandler bootstrap");
    }
}
