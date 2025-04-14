package com.example.event.Service;

import com.example.event.Model.Artist;
import com.example.event.Repository.ArtistRepository;
import com.example.event.Repository.PurchaseRepository;
import com.example.event.Repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ArtistService {

    final ArtistRepository artistRepository;
    final PurchaseRepository purchaseRepository;
    final TicketRepository ticketRepository;

    public ArtistService(ArtistRepository artistRepository, PurchaseRepository purchaseRepository, TicketRepository ticketRepository) {
        this.artistRepository = artistRepository;
        this.purchaseRepository = purchaseRepository;
        this.ticketRepository = ticketRepository;
    }

    public List getall(){
        return artistRepository.findAll();
    }

    public Boolean add(Artist artist){
        artistRepository.save(artist);
        return true;
    }

    public boolean updateArtist(Artist artist,int id){
        Artist oldArtist=artistRepository.getArtistById(id);
        if (oldArtist==null){
            return false;
        }
        oldArtist.setName(artist.getName());
        oldArtist.setField(artist.getField());
        oldArtist.setOriginCountry(artist.getOriginCountry());
        artistRepository.save(oldArtist);
        return true;
    }

    public boolean deleteArtist(int id){
        Artist oldeArtist=artistRepository.getArtistById(id);
        if (oldeArtist==null){
            return false;
        }
        artistRepository.delete(oldeArtist);
        return true;
    }

    //----------------------------------------------------------------------------





    public String upgradeFeaturedArtists(Double artistId) {
        List artistIds = Collections.singletonList(ticketRepository.getTotalSalesByArtist(artistId));

        if (artistIds.isEmpty()) {
            return "No artists passed the sales threshold";
        }
        List<Artist> artists = artistRepository.findAllById(artistIds);
        for (Artist artist : artists) {
            artist.setFeatured(true);
        }
        artistRepository.saveAll(artists);
        return "Upgraded " + artists.size() + " artists to Featured";
    }


}
