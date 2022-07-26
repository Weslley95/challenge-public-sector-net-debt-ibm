package com.ibm.publicsectordebt.controllers;

import com.ibm.publicsectordebt.entities.BcData;
import com.ibm.publicsectordebt.services.BcDataService;
import io.swagger.annotations.ApiOperation;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Classe para disponibilizar endpoints da aplicacao
 */
@RestController
@RequestMapping("/data")
@ApiOperation(value = "Bc Data Controller API")
public class BcDataController {

    @Autowired
    private BcDataService bcDataService;

    /**
     * GET - Retornar todos os dados em uma unica pagina
     * @return list
     */
    @GetMapping(value = "/all", produces="application/json")
    @ApiOperation(value = "Buscar todos os dados")
    public ResponseEntity<List<BcData>> findAll() {
        return ResponseEntity.ok().body(this.bcDataService.findAll());
    }

    /**
     * GET - Retornar todos os dados paginados
     * @return list
     */
    @GetMapping(value = "/all-pagination")
    @ApiOperation(value = "Buscar todos os dados paginados", produces="application/json")
    public ResponseEntity<Page<BcData>> findAllFilter(@PageableDefault(page = 0, size = 15) Pageable pageable) {
        return ResponseEntity.ok().body(this.bcDataService.findAllPage(pageable));

    }

    /**
     * GET - Retornar dados por id do banco de dados
     * @param id identificacao do objeto
     * @return obj
     */
    @GetMapping(value = "/{id}", produces="application/json")
    @ApiOperation(value = "Buscar dado por ID")
    public ResponseEntity<BcData> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.bcDataService.findById(id));
    }

    /**
     * GET - Retornar dados por data
     * @param startDate Data minima
     * @param endDate Data maxima
     * @return list date
     */
    @GetMapping(value = "/search-date-interval", produces="application/json")
    @ApiOperation(value = "Buscar data por intervalo")
    public ResponseEntity<List<BcData>> searchIntervalDate(
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
            @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate ) {
        return ResponseEntity.ok().body(this.bcDataService.searchDateStartEnd(startDate, endDate));
    }

    /**
     * GET - Retornar data filtrada
     * @param field tipo do campo (dia, mes ou ano)
     * @param value valor a ser pesquisado
     * @return year
     */
    @GetMapping(value = "/search-date", produces="application/json")
    @ApiOperation(value = "Buscar data por ano, mes ou dia")
    public ResponseEntity<List<BcData>> searchDate(
            @RequestParam(defaultValue = "") String field,
            @RequestParam(defaultValue = "") Integer value ) {
        return ResponseEntity.ok(this.bcDataService.searchDateField(field, value));
    }

    /**
     * POST - Inserir dados
     * @param obj objeto a ser inserido
     * @return create
     */
    @PostMapping(produces="application/json")
    @ApiOperation(value = "Inserir dado")
    public ResponseEntity<List<BcData>> insert(@RequestBody List<BcData> obj) {
        // Retornar 201 apos inserir dados e os dados criado
        return ResponseEntity
                .created(
                        ServletUriComponentsBuilder
                        .fromCurrentRequest()
                        .buildAndExpand()
                        .toUri())
                .body(this.bcDataService.insert(obj));
    }

    /**
     * PUT - Atualizar dados
     * @param objBcData Objeto a ser atualizado
     * @param id identificacao do objeto
     * @return ResponseEntity
     */
    @PutMapping(value = "/{id}", produces="application/json", consumes="application/json")
    @ApiOperation(value = "Atualizar dado")
    public ResponseEntity<BcData> update(@RequestBody BcData objBcData, @PathVariable Long id) {
        objBcData.setId(id);
        return ResponseEntity.ok().body(this.bcDataService.update(objBcData));
    }

    /**
     * DELETE - Deletar dado especifico
     * @param id identificacao do objeto
     * @return noContent()
     */
    @Transactional
    @DeleteMapping(value = "/{id}", produces="application/json")
    @ApiOperation(value = "Deletar dado especifico")
    public ResponseEntity<BcData> delete(@PathVariable Long id) {
        this.bcDataService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * DELETE - Deletar todos os dados
     * @return noContent()
     */
    @Transactional
    @DeleteMapping(value = "/delete-all", produces="application/json")
    @ApiOperation(value = "Deletar todos os dados")
    public ResponseEntity<Void> deleteAll() {
        this.bcDataService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    /**
     * Retornar dados por ano e gerar soma acumulada (12 meses)
     * @param year ano a ser filtrado
     * @return result
     */
    @GetMapping(value = "/debt/{year}", produces="application/json")
    @ApiOperation(value = "Buscar dados por ano e retornar soma acumulada")
    public ResponseEntity<JSONObject> sumAccumulated(@PathVariable Integer year) {
        // Retornar 201 apos inserir dados da API externa
        return ResponseEntity.ok().body(this.bcDataService.debtCalc(year));
    }

    /**
     * Resumo dos dados
     * @return list
     */
    @GetMapping(value = "/summary", produces="application/json")
    @ApiOperation(value = "Resumo de alguns dados na aplicação")
    public ResponseEntity<JSONObject> summary() {
        return ResponseEntity.ok().body(this.bcDataService.summary());
    }
}
