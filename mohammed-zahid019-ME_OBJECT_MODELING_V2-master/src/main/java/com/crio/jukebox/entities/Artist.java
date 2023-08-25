package com.crio.jukebox.entities;


    public class Artist extends BaseEntity {
        private final String artistName;
    
        public Artist(String id, String artistName) {
            this.artistName = artistName;
            this.id = id;
        }
    
        public Artist(String artistName) {
            this.artistName = artistName;
        }
    
        public Artist(Artist artist) {
            this(artist.id, artist.artistName);
        }
        
        public String getArtistName() {
            return artistName;
        }
    
        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((id == null) ? 0 : id.hashCode());
            return result;
        }
    
    
        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            User other = (User) obj;
            if (id == null) {
                if (other.id != null)
                    return false;
            } else if (!id.equals(other.id))
                return false;
            return true;
        }
    
        @Override
        public String toString() {
            return "Artist [artistName=" + artistName + "]";
        }
    
    }
    

