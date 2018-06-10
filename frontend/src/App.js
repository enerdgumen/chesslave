import React from 'react'
import { MuiThemeProvider, createMuiTheme, withStyles } from '@material-ui/core/styles'
import AppBar from '@material-ui/core/AppBar'
import Button from '@material-ui/core/Button'
import Toolbar from '@material-ui/core/Toolbar'
import Typography from '@material-ui/core/Typography'
import CssBaseline from '@material-ui/core/CssBaseline'
import FontAwesomeIcon from '@fortawesome/react-fontawesome'
import faChess from '@fortawesome/fontawesome-free-solid/faChess'
import { inject } from 'mobx-react'

const muiTheme = createMuiTheme({
  palette: {
    type: 'dark',
  },
})

const styles = theme => ({
  button: {
    margin: theme.spacing.unit,
  },
})

const App = ({ classes, events }) => {
  const selectBoard = () => events
      .rxSend('select-board')
      .subscribe(({ body: selected }) => { console.log('Selected?', selected) })
  const startGame = () => events
      .rxSend('start-game', { turn: 'WHITE', white: 'human', black: 'machine' })
      .subscribe(({ body }) => { console.log('Started', body) })
  return (
    <MuiThemeProvider theme={muiTheme}>
      <CssBaseline />
      <AppBar position="static" color="default">
        <Toolbar>
          <FontAwesomeIcon icon={faChess} size="2x" style={{ marginRight: '1rem' }} />
          <Typography variant="title" color="inherit">Chesslave</Typography>
        </Toolbar>
      </AppBar>
      <Button variant="raised" color="primary" className={classes.button} onClick={selectBoard}>
        Select board
      </Button>
      <Button variant="raised" color="secondary" className={classes.button} onClick={startGame}>
        Start game
      </Button>
    </MuiThemeProvider>
  )
}

export default inject('events')(withStyles(styles)(App))
