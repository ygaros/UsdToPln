package org.ygaros.usdtopln.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.ygaros.usdtopln.data.Computer;
import org.ygaros.usdtopln.data.ComputerId;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ComputerRepository extends JpaRepository<Computer, ComputerId> {

    List<Computer> findByIdDate(String date);

    /**
     * Both parameters needed
     */
    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%))")
    List<Computer> findByPartialNameAndDate(@Param("name") String name,
                                            @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.name asc")
    List<Computer> findByPartialNameAndDateOrderByNameAsc(@Param("name") String name,
                                                          @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.name desc")
    List<Computer> findByPartialNameAndDateOrderByNameDesc(@Param("name") String name,
                                                           @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date desc")
    List<Computer> findByPartialNameAndDateOrderByDateDesc(@Param("name") String name,
                                                           @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date asc")
    List<Computer> findByPartialNameAndDateOrderByDateAsc(@Param("name") String name,
                                                          @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date asc, c.id.name asc")
    List<Computer> findByPartialNameAndDateOrderByDateAscNameAsc(@Param("name") String name,
                                                                 @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date asc, c.id.name desc")
    List<Computer> findByPartialNameAndDateOrderByDateAscNameDesc(@Param("name") String name,
                                                                  @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date desc, c.id.name desc")
    List<Computer> findByPartialNameAndDateOrderByDateDescNameDesc(@Param("name") String name,
                                                                   @Param("date") String date);

    @Query("select c from Computer c where ((c.id.name like %:name%) and (c.id.date like %:date%)) order by c.id.date desc, c.id.name asc")
    List<Computer> findByPartialNameAndDateOrderByDateDescNameAsc(@Param("name") String name,
                                                                  @Param("date") String date);

}
