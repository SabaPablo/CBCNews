package com.doce.cactus.saba.feq.repositories

import com.doce.cactus.saba.feq.apis.NewsApi
import com.doce.cactus.saba.feq.dao.NewsDao
import com.doce.cactus.saba.feq.models.News
import com.doce.cactus.saba.feq.models.Show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.time.LocalDateTime

class ShowRepositoryImpl(): ShowRepository {
    override fun getShows(): Flow<List<Show>> {
        return flow {
            emit(listOf<Show>(
                Show(1,"Celin Dion",1,"https://mobile-img.lpcdn.ca/lpca/924x/9997e3f7/07e05114-dac5-11e9-a65c-0eda3a42da3c.jpg"),
                Show(2,"Mickel Jackson",2,"https://image.cnbcfm.com/api/v1/image/102926320-GettyImages-92535411.jpg?v=1532564267"),
                Show(3,"Maron 5",3,"https://media.npr.org/assets/music/news/2010/09/maroon-e9cb8c5b25b4d1f3e68aa26e6a0ce51cf2ae59d8-s1100-c50.jpg"),
                Show(4,"Rage against the machine",4,"https://www.billboard.com/wp-content/uploads/media/Rage-Against-The-Machine-1993-portrait-a-billboard-1548.jpg"),
                Show(5,"Led Zeppelin",5,"https://imageio.forbes.com/blogs-images/markbeech/files/2018/01/Led-Zeppelin-©-Neil-Zlozower-Six-Months-Use-Only-Do-Not-Use-After-of-July-24-2018_preview-1200x798.jpg?format=jpg&width=960"),
                Show(6,"Pink Floyd",6,"https://www.rollingstone.com/wp-content/uploads/2018/06/pink-floyd-1973-32d763d9-8deb-4cd8-a287-92ae0de7df3a.jpg"),
                Show(7,"Los Redondos de Ricota",7,"https://i.ytimg.com/vi/97whhQfGwVs/maxresdefault.jpg"),
                Show(8,"Mercedes Sosa",8,"https://static.eldiario.es/clip/bbbb1f31-fbfc-4e50-b56b-2114e23c9c51_16-9-discover-aspect-ratio_default_0.jpg"),
                Show(9,"The Beatles",9,"https://ichef.bbci.co.uk/news/976/cpsprodpb/14208/production/_109104428__108240741_beatles-abbeyroad-square-reuters-applecorps.jpg"),
                Show(10,"The Doors",10,"https://upload.wikimedia.org/wikipedia/commons/6/60/Doors_electra_publicity_photo.JPG"),
                Show(11,"Celin Dion",11,"https://zamusic.org/wp-content/uploads/2019/11/ALBUM-Celine-Dion-–-Courage-zamusic.jpg"),
                Show(12,"Celin Dion",12,"https://zamusic.org/wp-content/uploads/2019/11/ALBUM-Celine-Dion-–-Courage-zamusic.jpg"),
                Show(13,"Celin Dion",13,"https://zamusic.org/wp-content/uploads/2019/11/ALBUM-Celine-Dion-–-Courage-zamusic.jpg"),
                Show(14,"Celin Dion",14,"https://zamusic.org/wp-content/uploads/2019/11/ALBUM-Celine-Dion-–-Courage-zamusic.jpg"),
                Show(15,"Celin Dion",13,"https://zamusic.org/wp-content/uploads/2019/11/ALBUM-Celine-Dion-–-Courage-zamusic.jpg"),


                ))
        }.flowOn(Dispatchers.IO)
    }


}