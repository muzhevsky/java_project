document.addEventListener("DOMContentLoaded", ()=>{
    materialTableRowNumbers.set('1',1);
    labourTableRowNumbers.set('1',1);

    for (let i = 1; i < 3; i++){
        let priceInput = document.getElementById("1-"+i+"-1-price-input");
        let amountInput = document.getElementById("1-"+i+"-1-amount-input");
        let fullPrice = document.getElementById("1-"+i+"-1-fullPrice");

        priceInput.addEventListener("change", ()=>{
            if (priceInput.value.length > 0 && amountInput.value.length > 0)
                fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
        });
        amountInput.addEventListener("change", ()=>{
            if (priceInput.value.length > 0 && amountInput.value.length > 0)
                fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
        });
    }
});

let materialTableRowNumbers = new Map();
let labourTableRowNumbers = new Map();
let sectionNumber = 1;

function AddRow(id){
    let splittedId = id.split('-');
    let sectionId = splittedId[0];
    let typeId = splittedId[1];
    console.log(typeId);
    let map = typeId === '1' ? materialTableRowNumbers : labourTableRowNumbers;
    let newId = map.get(sectionId)+1;
    map.set(sectionId, newId);
    addRowHelper(sectionId, typeId, newId);
}

function addRowHelper(sectionId, typeId, newId){
    let table = document.getElementById(sectionId+'-'+typeId+'-table');
    let newRow = table.insertRow();
    newRow.id = sectionId+"-"+typeId+"-"+newId+"-row";
    newRow.innerHTML =
        "<td><input id=\'"+sectionId+"-"+typeId+"-"+newId+"-name-input\' type='text' max='128' required></td>" +
        "<td><input id=\'"+sectionId+"-"+typeId+"-"+newId+"-unit-input\' type='text' max='16' required></td>" +
        "<td><input id=\'"+sectionId+"-"+typeId+"-"+newId+"-amount-input\' type='number' required></td>" +
        "<td><input id=\'"+sectionId+"-"+typeId+"-"+newId+"-price-input\' type='number' required></td>" +
        "<td id=\'"+sectionId+"-"+typeId+"-"+newId+"-fullPrice\'>0.0</td>" +
        "<td><a id=\'"+sectionId+"-"+typeId+"-"+newId+"-deleteButton\' href='#' onClick='RemoveRow(this.id)'>Удалить строку</a></td>"

    let priceInput = document.getElementById(sectionId+"-"+typeId+"-"+newId+"-price-input");
    let amountInput = document.getElementById(sectionId+"-"+typeId+"-"+newId+"-amount-input");
    let fullPrice = document.getElementById(sectionId+"-"+typeId+"-"+newId+"-fullPrice");

    priceInput.addEventListener("change", ()=>{
        if (priceInput.value.length > 0 && amountInput.value.length > 0)
            fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
    });
    amountInput.addEventListener("change", ()=>{
        if (priceInput.value.length > 0 && amountInput.value.length > 0)
            fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
    });
}

function RemoveRow(id){
    let splittedId = id.split('-');
    let row = document.getElementById(splittedId[0]+'-'+splittedId[1]+'-'+splittedId[2]+'-row');
    row.remove();
}

function AddEstimateSection(){
    sectionNumber++;
    let newSection = document.getElementById("section-container")
        .appendChild(document.createElement("div"));
    newSection.innerHTML =
        "<div id=\'"+sectionNumber+"-section\' class=\'estimateCreation-section\'>" +
        "<h4 style=\'display: inline-block\'>Название раздела: </h4>" +
        "<input class=\"form-control\" id='"+sectionNumber+"-name-input' type='text' max='128' required>" +
            "<br>" +
            "<h4>Материалы: </h4>" +
            "<table>" +
                "<thead>" +
                "<tr id='"+sectionNumber+"-1-header'>" +
                    "<th>название</th>" +
                    "<th>ед.измерения</th>" +
                    "<th>кол-во</th>" +
                    "<th>цена</th>" +
                    "<th>стоимость</th>" +
                    "<th>удалить</th>" +
                "</tr>" +
                "</thead>" +
                "<tbody id='"+sectionNumber+"-1-table'>" +
                "<tr id='"+sectionNumber+"-1-1-row'>" +
                    "<td><input id='"+sectionNumber+"-1-1-name-input' type='text' max='128' required></td>" +
                    "<td><input id='"+sectionNumber+"-1-1-unit-input' type='text' max='16' required></td>" +
                    "<td><input id='"+sectionNumber+"-1-1-amount-input' type='number' required></td>" +
                    "<td><input id='"+sectionNumber+"-1-1-price-input' type='number' required></td>" +
                    "<td id='"+sectionNumber+"-1-1-fullPrice'>0.0</td>" +
                "</tr>" +
                "</tbody>" +
            "</table>" +
            "<button class='btn btn-primary' id='"+sectionNumber+"-1-addButton' type='button' onClick='AddRow(this.id)'>" +
                "Добавить строку" +
            "</button>" +
            "<br>" +
            "<br>" +
            "<h4>Работы: </h4>" +
            "<table>" +
                "<thead>" +
                "<tr id='"+sectionNumber+"-2-header'>" +
                    "<th>название</th>"+
                    "<th>ед.измерения</th>"+
                    "<th>кол-во</th>"+
                    "<th>цена</th>"+
                    "<th>стоимость</th>"+
                    "<th>удалить</th>"+
                "</tr>"+
                "</thead>"+
                "<tbody id='"+sectionNumber+"-2-table'>"+
                "<tr id='"+sectionNumber+"-2-1-row'>" +
                    "<td><input id='"+sectionNumber+"-2-1-name-input' type='text' max='128' required></td>"+
                    "<td><input id='"+sectionNumber+"-2-1-unit-input' type='text' max='16' required></td>"+
                    "<td><input id='"+sectionNumber+"-2-1-amount-input' type='number' required></td>"+
                    "<td><input id='"+sectionNumber+"-2-1-price-input' type='number' required></td>"+
                    "<td id='"+sectionNumber+"-2-1-fullPrice'>0.0</td>"+
                "</tr>"+
                "</tbody>"+
            "</table>"+
            "<button class='btn btn-primary' id='"+sectionNumber+"-2-addButton' type='button' onClick='AddRow(this.id)'>"+
                "Добавить строку"+
            "</button>"+
            "<br>"+
            "<button class='btn btn-primary' id='"+sectionNumber+"-removeEstimateButton' type='button' onClick='RemoveEstimateSection(this.id)'>"+
            "Удалить раздел"+
            "</button>"+
    "</div>";

    let priceInput = document.getElementById(sectionNumber+"-1-1-price-input");
    let amountInput = document.getElementById(sectionNumber+"-1-1-amount-input");
    let fullPrice = document.getElementById(sectionNumber+"-1-1-fullPrice");

    priceInput.addEventListener("change", ()=>{
        if (priceInput.value.length > 0 && amountInput.value.length > 0)
            fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
    });
    amountInput.addEventListener("change", ()=>{
        if (priceInput.value.length > 0 && amountInput.value.length > 0)
            fullPrice.innerHTML = (Number(priceInput.value) * Number(amountInput.value)).toFixed(2);
    });

    materialTableRowNumbers.set(sectionNumber.toString(),1);
    labourTableRowNumbers.set(sectionNumber.toString(),1);
}

function RemoveEstimateSection(id){
    let splittedId = id.split('-');
    document.getElementById(splittedId[0]+"-section").remove();
}

function CreateEstimate(){
    let sections = [];
    for (let i = 0; i <= sectionNumber; i++){
        if (!document.getElementById(i.toString()+'-section'))
            continue;

        let name = document.getElementById(i+'-name-input').value;
        let labourUnits = [];
        let materialUnits = [];
        console.log(materialTableRowNumbers.get(i.toString()));
        console.log(labourTableRowNumbers.get(i.toString()));
        for (let j = 0; j <= materialTableRowNumbers.get(i.toString()); j++){
            if (!document.getElementById(i+'-1-'+j+'-row'))
                continue;
            materialUnits.push({
                name: document.getElementById(i+'-1-'+j+'-name-input').value,
                unit: document.getElementById(i+'-1-'+j+'-unit-input').value,
                amount: Number(document.getElementById(i+'-1-'+j+'-amount-input').value),
                price: Number(document.getElementById(i+'-1-'+j+'-price-input').value)
            });
        }

        for (let j = 0; j <= labourTableRowNumbers.get(i.toString()); j++){
            if (!document.getElementById(i+'-2-'+j+'-row'))
                continue;
            labourUnits.push({
                name: document.getElementById(i+'-2-'+j+'-name-input').value,
                unit: document.getElementById(i+'-2-'+j+'-unit-input').value,
                amount: Number(document.getElementById(i+'-2-'+j+'-amount-input').value),
                price: Number(document.getElementById(i+'-2-'+j+'-price-input').value)
            });
        }

        sections.push({
            name: name,
            labourUnits: labourUnits,
            materialUnits: materialUnits
        });
    }

    let headers = new Headers();
    headers.set('Content-Type', 'application/json;charset=UTF-8');
    let estimate = {
        name: document.getElementById('projectOriginalName-input').value,
        shortDescription: document.getElementById('estimateShortDescription-input').value,
        sections: sections
    }
    fetch(window.location, {
        method: 'post',
        headers: headers,
        body: JSON.stringify(estimate)
    })
        .then((response)=>{
            if (response.status == 200)
                window.location = myProjectsPage;
        });
}