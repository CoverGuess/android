package info.acidflow.coverguess.datamodel;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import info.acidflow.coverguess.datamodel.api.AlbumAPI;

/**
 * Created by paul on 21/06/14.
 */
@Table(name = "ALBUMS", id = "_id" )
public class Album extends Model {

    @Column( name = "album_id", index = true, notNull = true, unique = true )
    private long mAlbumId;

    @Column( name = "album_title", index = true, notNull = true )
    private String mTitle;

    @Column( name = "album_artist", index = true, notNull = true )
    private String mArtist;
    /** Not-null value. */
    @Column( name = "album_cover_url", notNull = true )
    private String mCoverUrl;

    @Column( name = "album_last_updated" )
    private long mLastUpdateTimestamp;

    @Column( name = "album_category" )
    private Category mCategory;

    public Album(){
        super();
    }

    public Album( long id, String title, String artist, String coverUrl ){
        this(id, title, artist, coverUrl, 0, null);
    }

    public Album( long id, String title, String artist, String coverUrl, long lastUpdatedTimestamp, Category category ){
        super();
        mAlbumId = id;
        mTitle = title;
        mArtist = artist;
        mCoverUrl = coverUrl;
        mLastUpdateTimestamp = lastUpdatedTimestamp;
        mCategory = category;
    }

    public Album( AlbumAPI api){
        super();
        mAlbumId = api.id;
        mTitle = api.album_name;
        mArtist = api.album_artist;
        mCoverUrl = api.image_url;
        mLastUpdateTimestamp = api.last_update;
    }

    public long getAlbumId() {
        return mAlbumId;
    }

    public void setAlbumId(long mAlbumId) {
        this.mAlbumId = mAlbumId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String mArtist) {
        this.mArtist = mArtist;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }

    public void setCoverUrl(String mCoverUrl) {
        this.mCoverUrl = mCoverUrl;
    }

    public long getLastUpdateTimestamp() {
        return mLastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(long mLastUpdateTimestamp) {
        this.mLastUpdateTimestamp = mLastUpdateTimestamp;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category mCategory) {
        this.mCategory = mCategory;
    }
}
