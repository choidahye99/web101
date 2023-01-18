import React, { useState } from "react";
import ListItem from '@mui/material/ListItem';
import Checkbox from '@mui/material/Checkbox';
import ListItemText from '@mui/material/ListItemText';
import InputBase from '@mui/material/InputBase';
import { ListItemSecondaryAction, IconButton } from "@mui/material";
import { DeleteOutline } from "@mui/icons-material";
import { FavoriteBorder, Favorite } from '@mui/icons-material';
import { pink } from '@mui/material/colors'

const Todo = (props) => {

     // eslint-disable-next-line
    const [item, setItem] = useState(props.item);
    const[readOnly, setReadOnly] = useState(true);

    const turnOffReadOnly = () => {
        setReadOnly(false);
    }

    const turnOnReadOnly = (e) => {
        if(e.key === 'Enter' && readOnly === false){
            setReadOnly(true);
            editItem(item);
        }
    }

    const deleteItem = props.deleteItem;

    const deleteEventHandler = () => {
        deleteItem(item);
    }

    const editItem = props.editItem;

    const editEventHandler = (e) => {
        setItem({...item, title : e.target.value})
    }

    const checkBoxHandler = (e) => {
        item.done = e.target.checked;
        editItem(item);
    }


    return (
        <ListItem>
            <Checkbox 
            icon={<FavoriteBorder />} 
            checkedIcon={<Favorite />}
            sx={{
                color: pink[800],
                '&.Mui-checked': {
                  color: pink[600],
                },
              }}
            checked={item.done} 
            onChange={checkBoxHandler} />
            <ListItemText>
               <InputBase
                    inputProps={{"aria-label" : "naked",
                                readOnly : readOnly }}
                    onClick = {turnOffReadOnly}
                    onKeyDown = {turnOnReadOnly}
                    onChange = {editEventHandler}
                    type="text" 
                    id={item.id} 
                    name={item.id} 
                    value={item.title} 
                    multiline={true}
                    fullWidth={true}/>
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutline />
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>

    )
} 

export default Todo;