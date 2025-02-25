package com.htksoft.reservation.config;

import com.htksoft.reservation.entity.City;
import com.htksoft.reservation.repository.CityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CityRepository cityRepository;

    @Override
    public void run(String... args) {
        initializeCities();
    }

    private void initializeCities() {
        if (cityRepository.count() == 0) {
            List<City> cities = Arrays.asList(
                City.builder().id(1L).name("Adana").build(),
                City.builder().id(2L).name("Adıyaman").build(),
                City.builder().id(3L).name("Afyonkarahisar").build(),
                City.builder().id(4L).name("Ağrı").build(),
                City.builder().id(5L).name("Amasya").build(),
                City.builder().id(6L).name("Ankara").build(),
                City.builder().id(7L).name("Antalya").build(),
                City.builder().id(8L).name("Artvin").build(),
                City.builder().id(9L).name("Aydın").build(),
                City.builder().id(10L).name("Balıkesir").build(),
                City.builder().id(11L).name("Bilecik").build(),
                City.builder().id(12L).name("Bingöl").build(),
                City.builder().id(13L).name("Bitlis").build(),
                City.builder().id(14L).name("Bolu").build(),
                City.builder().id(15L).name("Burdur").build(),
                City.builder().id(16L).name("Bursa").build(),
                City.builder().id(17L).name("Çanakkale").build(),
                City.builder().id(18L).name("Çankırı").build(),
                City.builder().id(19L).name("Çorum").build(),
                City.builder().id(20L).name("Denizli").build(),
                City.builder().id(21L).name("Diyarbakır").build(),
                City.builder().id(22L).name("Edirne").build(),
                City.builder().id(23L).name("Elazığ").build(),
                City.builder().id(24L).name("Erzincan").build(),
                City.builder().id(25L).name("Erzurum").build(),
                City.builder().id(26L).name("Eskişehir").build(),
                City.builder().id(27L).name("Gaziantep").build(),
                City.builder().id(28L).name("Giresun").build(),
                City.builder().id(29L).name("Gümüşhane").build(),
                City.builder().id(30L).name("Hakkari").build(),
                City.builder().id(31L).name("Hatay").build(),
                City.builder().id(32L).name("Isparta").build(),
                City.builder().id(33L).name("Mersin").build(),
                City.builder().id(34L).name("İstanbul").build(),
                City.builder().id(35L).name("İzmir").build(),
                City.builder().id(36L).name("Kars").build(),
                City.builder().id(37L).name("Kastamonu").build(),
                City.builder().id(38L).name("Kayseri").build(),
                City.builder().id(39L).name("Kırklareli").build(),
                City.builder().id(40L).name("Kırşehir").build(),
                City.builder().id(41L).name("Kocaeli").build(),
                City.builder().id(42L).name("Konya").build(),
                City.builder().id(43L).name("Kütahya").build(),
                City.builder().id(44L).name("Malatya").build(),
                City.builder().id(45L).name("Manisa").build(),
                City.builder().id(46L).name("Kahramanmaraş").build(),
                City.builder().id(47L).name("Mardin").build(),
                City.builder().id(48L).name("Muğla").build(),
                City.builder().id(49L).name("Muş").build(),
                City.builder().id(50L).name("Nevşehir").build(),
                City.builder().id(51L).name("Niğde").build(),
                City.builder().id(52L).name("Ordu").build(),
                City.builder().id(53L).name("Rize").build(),
                City.builder().id(54L).name("Sakarya").build(),
                City.builder().id(55L).name("Samsun").build(),
                City.builder().id(56L).name("Siirt").build(),
                City.builder().id(57L).name("Sinop").build(),
                City.builder().id(58L).name("Sivas").build(),
                City.builder().id(59L).name("Tekirdağ").build(),
                City.builder().id(60L).name("Tokat").build(),
                City.builder().id(61L).name("Trabzon").build(),
                City.builder().id(62L).name("Tunceli").build(),
                City.builder().id(63L).name("Şanlıurfa").build(),
                City.builder().id(64L).name("Uşak").build(),
                City.builder().id(65L).name("Van").build(),
                City.builder().id(66L).name("Yozgat").build(),
                City.builder().id(67L).name("Zonguldak").build(),
                City.builder().id(68L).name("Aksaray").build(),
                City.builder().id(69L).name("Bayburt").build(),
                City.builder().id(70L).name("Karaman").build(),
                City.builder().id(71L).name("Kırıkkale").build(),
                City.builder().id(72L).name("Batman").build(),
                City.builder().id(73L).name("Şırnak").build(),
                City.builder().id(74L).name("Bartın").build(),
                City.builder().id(75L).name("Ardahan").build(),
                City.builder().id(76L).name("Iğdır").build(),
                City.builder().id(77L).name("Yalova").build(),
                City.builder().id(78L).name("Karabük").build(),
                City.builder().id(79L).name("Kilis").build(),
                City.builder().id(80L).name("Osmaniye").build(),
                City.builder().id(81L).name("Düzce").build()
            );
            
            cityRepository.saveAll(cities);
        }
    }
} 