package com.jukebox.repositories;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Map;
import java.util.HashMap;
import com.jukebox.entities.PlayList;

public class PlayListRepository implements IPlayListRepository{

    private final Map<String , PlayList> playListMap;
    //<User ID , <PlayList ID , Song ID>> userCurrentPlayList_SongMap
    private final Map<String, HashMap<String, String>> userCurrentPlayList_SongMap;

    private Integer autoIncrement = 0;

    public PlayListRepository() {
        playListMap = new HashMap<>();
        userCurrentPlayList_SongMap = new HashMap<>();
    }

    public PlayListRepository(Map<String, PlayList> playListMap,
                              Map<String, HashMap<String, String>> userCurrentPlayList_SongMap) {
        this.playListMap = playListMap;
        this.autoIncrement = playListMap.size();
        this.userCurrentPlayList_SongMap = userCurrentPlayList_SongMap;
    }

    @Override
    public PlayList save(PlayList entity) {
        if(entity.getPlayListId() == null){
            autoIncrement++;
            PlayList playList = new PlayList(Integer.toString(autoIncrement), entity.getPlayListName(),
                    entity.getPlayListSongs(), entity.getUserId());
            playListMap.put(playList.getPlayListId(), playList);
            return playList;
        }
        playListMap.put(entity.getPlayListId(), entity);
        return entity;
    }

    @Override
    public void delete(PlayList entity) {
        playListMap.remove(entity.getPlayListId());
    }

    @Override
    public void deleteById(String id) {
        playListMap.remove(id);
    }

    @Override
    public boolean existsById(String id) {
        return playListMap.containsKey(id);
    }

    @Override
    public List<PlayList> findAll() {
        return playListMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public Optional<PlayList> findById(String id) {
        return Optional.ofNullable(playListMap.get(id));
    }

    @Override
    public PlayList findBy_PlayListID_UserID(String playListId, String userId) {
        List<PlayList> playLists = this.findAll();
        Optional<PlayList> playList = playLists.stream()
                .filter(p -> p.getPlayListId().equals(playListId) &&
                        p.getUserId().equals(userId))
                .findFirst();
        return playList.isPresent() ? playList.get() : null;
    }

    @Override
    public void setCurrentPlayListOnUserID(String userId, String playListId, String songId) {
        HashMap<String, String> currMap = new HashMap<>();
        if(userCurrentPlayList_SongMap.containsKey(userId)){
            userCurrentPlayList_SongMap.remove(userId);
        }
        currMap.put(playListId , songId);
        userCurrentPlayList_SongMap.put(userId,currMap);
    }

    @Override
    public String getCurrentPlayListOnUserID(String userId) {
        HashMap<String,String> currMap = new HashMap<>();   //<PlayListID , SongID>
        currMap = userCurrentPlayList_SongMap.get(userId);  // currMap will get <PlayListID , SongID>
        return currMap.keySet().stream().findFirst().get(); // returning Key field - PlayList ID
    }

    @Override
    public void setCurrentSongOnPlayListOfUserID(String userId , String songId) {
        HashMap<String,String> currMap = new HashMap<>();   //<PlayListID , SongID>
        currMap = userCurrentPlayList_SongMap.get(userId);  // currMap will get <PlayListID , SongID>
        // Gets PlayListID
        String playListId = currMap.keySet()
                .stream()
                .findFirst()
                .get();
        currMap.put(playListId , songId);                   //PlayList ID updated with Song ID
        userCurrentPlayList_SongMap.put(userId , currMap);  //User updated with Current Song in PlayList
    }

    @Override
    public String getCurrentSongOnPlayListOfUserID(String userId) {
        HashMap<String,String> currMap = new HashMap<>();   //<PlayListID , SongID>
        currMap = userCurrentPlayList_SongMap.get(userId);  // currMap will get <PlayListID , SongID>
        return currMap.values().stream().findFirst().get(); // Returning current Song ID which User ID is playing in PlayList
    }

}
