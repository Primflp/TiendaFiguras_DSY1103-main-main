package tienda_figuras.ms_figuras.repository;

import tienda_figuras.ms_figuras.model.Figura;
import tienda_figuras.ms_figuras.model.StockReservadoDTO;
import tienda_figuras.ms_figuras.model.StockTotalDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface FiguraRepository extends JpaRepository<Figura, Long> {

    @Query("SELECT f.stockTotalFigura AS stockTotalFigura, f.nombreFigura AS nombreFigura FROM Figura f WHERE f.stockTotalFigura > 0 ")
    List<StockTotalDTO> verificarStockTotal ();
    //metodo custom 1

    @Query("SELECT f.stockReservadoFigura AS stockReservadoFigura, f.nombreFigura AS nombreFigura FROM Figura f WHERE f.stockReservadoFigura > 0 ")
    List<StockReservadoDTO> verificarStockReservado ();
    //metodo custom 2

    @Query("SELECT f FROM Figura f WHERE f.tamanoFigura = :tamano ")
    List<Figura> buscarPorTamano (float tamano);
    //metodo custom 3

    @Query("SELECT f FROM Figura f WHERE f.marcaFigura LIKE %:marca% ")
    List<Figura> buscarPorMarca (String marca);
    //metodo custom 4

    @Query("SELECT f FROM Figura f WHERE f.tipoCajaFigura LIKE %:tipo% ")
    List<Figura> buscarPorTipoCaja (String tipo);
    //metodo custom 5
    
}
