package com.jukebox.appConfig;

import com.jukebox.commands.*;
import com.jukebox.repositories.*;
import com.jukebox.services.*;

public class ApplicationConfig {
    private final IPlayListRepository playListRepository = new PlayListRepository();
    private final ISongsRepository songsRepository = new SongsRepository();
    private final IUserRepository userRepository = new UserRepository();

    private final IPlayListService playListService = new PlayListService(playListRepository, userRepository, songsRepository);
    private final ISongService songService = new SongService(songsRepository, playListRepository);
    private final IUserService userService = new UserService(userRepository);

    private final LoadDataCommand loadDataCommand = new LoadDataCommand(songService);
    private final CreatePlayListCommand createPlayListCommand = new CreatePlayListCommand(playListService);
    private final CreateUserCommand createUserCommand = new CreateUserCommand(userService);
    private final DeletePlayListCommand deletePlayListCommand = new DeletePlayListCommand(playListService);
    private final ModifyPlayListCommand modifyPlayListCommand = new ModifyPlayListCommand(playListService);
    private final PlayPlaylistCommand playPlaylistCommand = new PlayPlaylistCommand(playListService);
    private final PlaySongCommand playSongCommand = new PlaySongCommand(songService);

    private final CommandInvoker commandInvoker = new CommandInvoker();

    public CommandInvoker getCommandInvoker(){
        commandInvoker.register("LOAD-DATA", loadDataCommand);
        commandInvoker.register("CREATE-USER", createUserCommand);
        commandInvoker.register("CREATE-PLAYLIST", createPlayListCommand);
        commandInvoker.register("DELETE-PLAYLIST", deletePlayListCommand);
        commandInvoker.register("PLAY-PLAYLIST", playPlaylistCommand);
        commandInvoker.register("MODIFY-PLAYLIST", modifyPlayListCommand);
        commandInvoker.register("PLAY-SONG", playSongCommand);
        return commandInvoker;
    }
}

