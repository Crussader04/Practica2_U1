import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, ComboBox, DatePicker, Dialog, Grid, GridColumn, GridItemModel, GridSortColumn, HorizontalLayout, Icon, NumberField, Select, TextField, VerticalLayout } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';

import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';

import { useDataProvider } from '@vaadin/hilla-react-crud';
import Cancion from 'Frontend/generated/org/northpole/workshop/base/models/Cancion';
import { CancionService } from 'Frontend/generated/endpoints';
import { useEffect, useState } from 'react';

export const config: ViewConfig = {
  title: 'Cancion',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Cancion',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormUpdateProps = {
  onCancionUpdated?: () => void;
};

//Cancion CREATE
function CancionEntryForm(props: CancionEntryFormProps) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };


  const nombre = useSignal('');
  const Album = useSignal('');
  const Genero = useSignal('');
  const Url = useSignal('');
  const Duracion = useSignal('');
  const Tipo = useSignal('');
  const duracionError = useSignal('');

  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && Album.value.trim().length > 0 && Genero.value.trim().length > 0 && Url.value.trim().length > 0 && Duracion.value.trim().length > 0 && Tipo.value.trim().length > 0) {
        const id_genero = parseInt(Genero.value) + 1;
        const id_album = parseInt(Album.value) + 1;
        await CancionService.createCancion(nombre.value, parseFloat(Duracion.value), Url.value, Tipo.value, id_genero, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }
        nombre.value = '';
        Genero.value = '';
        Album.value = '';
        Url.value = '';
        Duracion.value = '';
        Tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listGeneroCombo().then(data =>
      listaGenero.value = data
    );
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumCombo().then(data =>
      listaAlbum.value = data
    );
  }, []);
  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      listaTipo.value = data
    );
  }, []);
  /*     const dialogOpened = useSignal(false); */
  return (
    <>
      <Dialog
        aria-label="Registrar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createCancion}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='nombre de la Cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox label="Album"
              items={listaAlbum.value}
              placeholder="Seleccione un Album"
              aria-label="Seleccione un Album"
              value={Album.value}
              onValueChanged={(evt) => (Album.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
              items={listaGenero.value}
              placeholder="Seleccione un genero de la lista"
              aria-label="Seleccione un genero"
              value={Genero.value}
              onValueChanged={(evt) => (Genero.value = evt.detail.value)}
            />
            <TextField label="Url"
              placeholder='Ingrese el link de la Cancion'
              aria-label='Ingrese Url'
              value={Url.value}
              onValueChanged={(evt) => (Url.value = evt.detail.value)}
            />
            <NumberField label="Duracion"
              step={0.01}
              value={Duracion.value}
              stepButtonsVisible
              errorMessage={duracionError.value}
              onValueChanged={(event) => {
                Duracion.value = event.detail.value;
              }}
              onValidated={(event) => {
                const field = event.target as any; // o NumberFieldElement si tienes el tipo
                const { validity } = field.inputElement as HTMLInputElement;
                if (validity.badInput) {
                  duracionError.value = 'Formato de número inválido';
                } else if (validity.stepMismatch) {
                  duracionError.value = `Debe ser múltiplo de ${field.step}`;
                } else {
                  duracionError.value = '';
                }
              }}
            />
            <ComboBox label="Tipo"
              items={listaTipo.value}
              placeholder="Seleccione un Tipo de la lista"
              aria-label="Seleccione un Tipo"
              value={Tipo.value}
              onValueChanged={(evt) => (Tipo.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Registrar</Button>
    </>
  );
}

//UPDATE Cancion
function CancionEntryFormUpdate(props: CancionEntryFormUpdateProps & { arguments: Cancion }) {
  const dialogOpened = useSignal(false);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  // Inicializa los campos con los valores actuales
  const nombre = useSignal(props.arguments.nombre ?? '');
  const Album = useSignal(props.arguments.album ?? '');
  const Genero = useSignal(props.arguments.genero ?? '');
  const Url = useSignal(props.arguments.url ?? '');
  const Duracion = useSignal(props.arguments.duracion?.toString() ?? '');
  const Tipo = useSignal(props.arguments.tipo ?? '');

  // Listas para los combos
  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listGeneroCombo().then(data =>
      listaGenero.value = data
    );
  }, []);

  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumCombo().then(data =>
      listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
      listaTipo.value = data
    );
  }, []);

  const updateCancion = async () => {
    try {
      if (
        nombre.value.trim().length > 0 &&
        Album.value.trim().length > 0 &&
        Genero.value.trim().length > 0 &&
        Url.value.trim().length > 0 &&
        Duracion.value.trim().length > 0 &&
        Tipo.value.trim().length > 0
      ) {
        const id_genero = parseInt(Genero.value) + 1;
        const id_album = parseInt(Album.value) + 1;
        await CancionService.updateCancion(
          props.arguments.id,
          nombre.value,
          parseFloat(Duracion.value),
          Url.value,
          Tipo.value,
          id_genero,
          id_album
        );
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }
        dialogOpened.value = false;
        Notification.show('Cancion actualizada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualizar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Editar Cancion"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Cancion
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateCancion}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la Cancion'
              aria-label='nombre de la Cancion'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox label="Album"
              items={listaAlbum.value}
              placeholder="Seleccione un Album"
              aria-label="Seleccione un Album"
              value={Album.value}
              onValueChanged={(evt) => (Album.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
              items={listaGenero.value}
              placeholder="Seleccione un genero de la lista"
              aria-label="Seleccione un genero"
              value={Genero.value}
              onValueChanged={(evt) => (Genero.value = evt.detail.value)}
            />
            <TextField label="Url"
              placeholder='Ingrese el link de la Cancion'
              aria-label='Ingrese Url'
              value={Url.value}
              onValueChanged={(evt) => (Url.value = evt.detail.value)}
            />
            <NumberField label="Duracion"
              placeholder='Ingrese la Duracion de la Cancion'
              aria-label='Duracion de la Cancion'
              value={Duracion.value}
              onValueChanged={(evt) => (Duracion.value = evt.detail.value)}
            />
            <ComboBox label="Tipo"
              items={listaTipo.value}
              placeholder="Seleccione un Tipo de la lista"
              aria-label="Seleccione un Tipo"
              value={Tipo.value}
              onValueChanged={(evt) => (Tipo.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button onClick={open}>Editar</Button>
    </>
  );
}
//*************************** */

const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});


export default function CancionView() {
  const [items, setItems] = useState([]);
  const callData = () => {
    CancionService.listCancion().then(function (data) {
      setItems(data);
    });
  };
  useEffect(() => {
    callData();
  }, []);


  const order = (event, columId) => {
    const direction = event.detail.value;
    if (direction == null) {
      // Sin orden: pide la lista original
      CancionService.listCancion().then(function (data) {
        setItems(data);
      });
    } else {
      var dir = direction == 'asc' ? 1 : 2;
      CancionService.order(columId, dir).then(function (data) {
        setItems(data);
      });
    }
  }

  function link({ item }: { item: Cancion }) {
    return (
      <span>
        <CancionEntryFormUpdate arguments={item} onCancionUpdated={callData}>

        </CancionEntryFormUpdate>
      </span>
    );
  }



  function index({ model }: { model: GridItemModel<Cancion> }) {
    return (
      <span>
        {model.index + 1}
      </span>
    );
  }

  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Nombre',
      value: 'nombre',
    },
    {
      label: 'Genero',
      value: 'genero',
    },
    {
      label: 'Duracion',
      value: 'duracion',
    },
    {
      label: 'Tipo',
      value: 'tipo',
    },
  ]

  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      CancionService.search(criterio.value, texto.value, 1).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  const albumBuscado = useSignal('');

  const searchAlbum = async () => {
    try {
      const res = await CancionService.searchAlbum(albumBuscado.value);
      const lista = Array.isArray(res) ? res : Object.values(res); // Convierte a array si no lo es

      if (lista && lista.length > 0) {
        setItems(lista);
        Notification.show('Álbum encontrado', { duration: 3000, theme: 'success' });
      } else {
        setItems([]);
        Notification.show('No se encontró el álbum', { duration: 3000, theme: 'error' });
      }
    } catch (e) {
      Notification.show('Error en la búsqueda', { duration: 3000, theme: 'error' });
    }
  };


  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Bandas">
        <Group>
          <CancionEntryForm onCancionCreated={callData} />
        </Group>
      </ViewToolbar>
      <HorizontalLayout theme="spacing">
        <Select items={itemSelect}
          value={criterio.value}
          onValueChanged={(evt) => (criterio.value = evt.detail.value)}
          placeholder="Selecione un cirterio">
        </Select>

        <TextField
          placeholder="Search"
          style={{ width: '50%' }}
          value={texto.value}
          onValueChanged={(evt) => (texto.value = evt.detail.value)}
        >
          <Icon slot="prefix" icon="vaadin:search" />
        </TextField>
        <Button onClick={search} theme="primary">
          BUSCAR
        </Button>
      </HorizontalLayout>
      <HorizontalLayout theme="spacing">
        <TextField
          placeholder="Nombre del álbum"
          value={albumBuscado.value}
          onValueChanged={e => (albumBuscado.value = e.detail.value)}
          style={{ width: '300px' }}
        />
        <Button onClick={searchAlbum} theme="primary">
          Buscar por álbum
        </Button>
      </HorizontalLayout>

      <Grid items={items}>
        <GridColumn header="Nro" renderer={index} />
        <GridSortColumn path="nombre" header="Nombre de la cancion" onDirectionChanged={(e) => order(e, 'nombres')} />
        <GridSortColumn path="album" header="Album" onDirectionChanged={(e) => order(e, 'album')} />
        <GridSortColumn path="genero" header="Genero" onDirectionChanged={(e) => order(e, 'genero')} />
        <GridColumn path="url" header="Url" />
        <GridSortColumn path="duracion" header="Duracion" onDirectionChanged={(e) => order(e, 'duracion')} />
        <GridSortColumn path="tipo" header="tipo de archivo" onDirectionChanged={(e) => order(e, 'tipo')} />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}

