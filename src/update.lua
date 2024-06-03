
TimeToMax = 80;
Deler     = 10  ;
SpeedOfSideways = 3;



function update(table)



    if  table["JmpCount"] <= 0 and table["coll"] == true then

        table["JmpCount"] = TimeToMax               --als collision komt als hij aan het vallen is dan zal Jumpcounter naar TimeTomax worden gezet

    elseif table["JmpCount"] >= -TimeToMax then

        table["JmpCount"] = table["JmpCount"] - 1    -- als springend gezet, gewoon minder snel laten springen (-1) (of sneller laten vallen)

    else
        table["JmpCount"] = table["JmpCount"]       --mss overbodig

    end



    table["PosY"] = table["PosY"] + (table["JmpCount"]/Deler)


    if table["dir"] == 0 then

        table["PosX"] = table["PosX"]


    else
        table["PosX"] = ((table["PosX"] + table["MaxX"] +( SpeedOfSideways * table["dir"] )) % table["MaxX"])  -- moet btr

    end


--index = (index + rangeLength + incrementAmount) % rangeLength

    table["Score"] = table["JmpCount"]/Deler

    return table

end
function GetScore()

    return

end