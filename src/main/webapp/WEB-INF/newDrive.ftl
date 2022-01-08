<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <title>Fahrt erstellen</title>
    <style>
        body{
            background: #eee;
        }
        .container{
            max-width: 800px;
        }
    </style>

</head>
<body>
<div class="container py-4">
    <div class="mb-3 rounded shadow-sm">
        <!-- Navbar -->
        <div class="mb-3 rounded shadow-sm">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
                <div>
                    <img
                            src="./Images/car.png"
                            width="30"
                            height="30"
                            class="d-inline-block align-top"
                            alt=""
                    />
                    <a class="navbar-brand" href="/view_main">CarSharer</a>
                </div>
                <!-- Hamburger Menu -->
                <button
                        class="navbar-toggler"
                        type="button"
                        data-bs-toggle="collapse"
                        data-bs-target="#navbarNavAltMarkup"
                        aria-controls="navbarNavAltMarkup"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                >
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                    <div class="navbar-nav">
                        <a class="nav-item nav-link active" href="/new_drive">Fahrt Erstellen</a>
                        <a class="nav-item nav-link active" href="/view_drive">Fahrt anzeigen</a>
                        <a class="nav-item nav-link active" href="/view_search">Fahrt suchen</a>
                        <a class="nav-item nav-link active" href="/bonus">Bester Fahrer</a>
                        <a class="nav-item nav-link active" href="/new_rating">Fahrt bewerten</a>
                    </div>
                </div>
            </nav>
        </div>
    </div>
    <div class="card mb-5 rounded shadow-sm">
        <div class="card-body">
                <h2>Fahrt erstellen</h2>
            <form>
                <div class="form-group row mb-2">
                    <label for="from" class="col-sm-3 col-form-label">Von:</label>
                    <div class="col-sm-9">
                        <input name="from"type="text" id="from" class="form-control">
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="to" class="col-sm-3 col-form-label">Bis:</label>
                    <div class="col-sm-9">
                        <input name="to" type="text" id="to" class="form-control">
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="capcity" class="col-sm-3 col-form-label">Maximale Kapzität:</label>
                    <div class="col-sm-9">
                        <input name="capacity" type="number" min="1" id="capcity" class="form-control">
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="cost" class="col-sm-3 col-form-label">Fahrtkosten:</label>
                    <div class="col-sm-9">
                        <input name="cost" type="number" min="1" id="cost" class="form-control">
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="" class="col-sm-3 col-form-label">Transportmittel:</label>
                    <div class="col-sm-9">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="Transportmittel" id="Auto" value="Auto" checked>
                            <label class="form-check-label" for="Auto">
                                Auto
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="Transportmittel" id="Bus" value="Bus">
                            <label class="form-check-label" for="Bus">
                                Bus
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="Transportmittel" id="Kleintransporter" value="Kleintransporter">
                            <label class="form-check-label" for="Kleintransporter">
                                Kleintransporter
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="Fahrtdatum" class="col-sm-3 col-form-label">Fahrtdatum:</label>
                    <div class="col-sm-5">
                        <input type="date" id="Fahrtdatum" name="trip-start" value="2022-02-12" min="2022-01-01" max="2022-12-31"class="form-control">
                    </div>
                    <div class="col-sm-4">
                        <input type="time" id="Fahrtdatum" name="appt" value="12:00"min="00:00" max="24:00" class="form-control">
                    </div>
                </div>
                <div class="form-group row mb-2">
                    <label for="Beschreibung" class="col-sm-3 col-form-label">Beschreibung:</label>
                    <div class="col-sm-9">
                        <input type="text" id="Beschreibung" name="description" class="form-control">
                    </div>
                </div>
                <button type="submit" class="btn btn-primary d-block mb-2 float-end">Erstellen</button>
            </form>
        </div>
    </div>

</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
</body>
</html>